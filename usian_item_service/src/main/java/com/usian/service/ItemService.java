package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemCatMapper;
import com.usian.mapper.TbItemDescMapper;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.*;
import com.usian.utils.IDUtils;
import com.usian.utils.PageResult;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public PageResult selectTbItemAllByPage(Integer page,Integer rows){
        PageHelper.startPage(page,rows);
        //查询状态是1 并且 按修改时间逆序排列
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated DESC");
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusEqualTo((byte)1);
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        for (int i = 0; i < tbItems.size(); i++) {
            TbItem tbItem = tbItems.get(i);
            tbItem.setPrice(tbItem.getPrice()/100);
        }
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);
        //返回PageResult
        PageResult pageResult = new PageResult();
        pageResult.setResult(tbItemPageInfo.getList());
        pageResult.setTotalPage(Long.valueOf(tbItemPageInfo.getPages()));
        pageResult.setPageIndex(tbItemPageInfo.getPageNum());
        return pageResult;
    }


    public List<TbItemCat> selectItemCategoryByParentId(Long id) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        criteria.andStatusEqualTo(1);
        List<TbItemCat> list = this.tbItemCatMapper.selectByExample(example);
        return list;

    }

    public Integer insertTbItem(TbItem tbItem, String desc, String itemParams) {
        //补齐tbitem数据
        long itemId = IDUtils.genItemId();
        Date date = new Date();
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setUpdated(date);
        tbItem.setCreated(date);
        tbItem.setPrice(tbItem.getPrice()*100);
        Integer tbItemNum = tbItemMapper.insertSelective(tbItem);

        //补齐商品描述对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        Integer tbitemDescNum = itemDescMapper.insertSelective(tbItemDesc);

        //补齐商品规格参数
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setCreated(date);
        Integer itemParemItemNum = itemParamItemMapper.insertSelective(tbItemParamItem);

        //添加商品发布消息到mq
        amqpTemplate.convertAndSend("item_exchage","item.add", itemId);
        return itemParemItemNum+tbitemDescNum+tbItemNum;
    }

    public Map<String, Object> preUpdateItem(Long itemId) {
        Map<String, Object> map = new HashMap<>();
        //根据商品ID查询商品
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        map.put("item",tbItem);
        //根据商品ID查询描述
        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        map.put("itemDesc",tbItemDesc.getItemDesc());
        //根据商品ID查询商品类目
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(tbItem.getCid());
        map.put("itemCat",tbItemCat.getName());
        //根据商品ID查询商品规格信息
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItemList = itemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        if(tbItemParamItemList!=null && tbItemParamItemList.size()>0){
            map.put("itemParamItem",tbItemParamItemList.get(0));
        }
        return map;
    }

    public Integer updateTbItem(TbItem tbItem, String desc, String itemParams) {
        Long tbItemId = tbItem.getId();
        tbItem.setUpdated(new Date());
        int i1 = tbItemMapper.updateByPrimaryKeySelective(tbItem);

        //修改商品描述对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());
        int i2 = itemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

        //修改商品规格参数
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(tbItemId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(new Date());
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(tbItemId);
        int i3 = itemParamItemMapper.updateByExampleSelective(tbItemParamItem, example);
        return i1+i2+i3;
    }

    public Integer deleteItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        tbItem.setStatus((byte) 0);
        int i = tbItemMapper.updateByPrimaryKey(tbItem);
        return i;
    }


}
