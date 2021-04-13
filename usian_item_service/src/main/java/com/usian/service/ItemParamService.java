package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamExample;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamService {

    @Autowired
    private TbItemParamMapper mapper;



    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = mapper.selectByExampleWithBLOBs(example);
        if (list.size()>0 && list!=null){
            return list.get(0);
        }
        return null;
    }

    public PageResult selectItemParamAll(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);

        List<TbItemParam> list = mapper.selectByExampleWithBLOBs(null);

        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setResult(pageInfo.getList());
        pageResult.setTotalPage(Long.valueOf(pageInfo.getPages()));
        pageResult.setTotalPage(pageInfo.getTotal());
        return pageResult;
    }

    public Integer insertItemParam(Long itemCatId, String paramData) {
        //判断该别的商品是否有规格模板
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = mapper.selectByExample(example);
        if (list.size()>0){
            return 0;
        }

        //保存规格模板
        Date date = new Date();
        TbItemParam param = new TbItemParam();
        param.setItemCatId(itemCatId);
        param.setParamData(paramData);
        param.setCreated(date);
        param.setUpdated(date);
        return mapper.insertSelective(param);
    }

    public Integer deleteItemParamById(Long id) {
        int i = mapper.deleteByPrimaryKey(id);
        return i;
    }
}
