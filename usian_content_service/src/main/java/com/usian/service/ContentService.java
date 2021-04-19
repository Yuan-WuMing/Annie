package com.usian.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbContentMapper;
import com.usian.pojo.TbContent;
import com.usian.pojo.TbContentExample;
import com.usian.utils.AdNode;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Value("${AD_CATEGORY_ID}")
    private Long AD_CATEGORY_ID;

    @Value("${AD_HEIGHT}")
    private Integer AD_HEIGHT;

    @Value("${AD_WIDTH}")
    private Integer AD_WIDTH;

    @Value("${AD_HEIGHTB}")
    private Integer AD_HEIGHTB;

    @Value("${AD_WIDTHB}")
    private Integer AD_WIDTHB;


    public PageResult selectTbContentAllByCategoryId(Long categoryId) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        PageHelper.startPage(1,10);
        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setTotalPage(pageInfo.getTotal());
        pageResult.setPageIndex(1);
        pageResult.setResult(pageInfo.getList());
        return pageResult;
    }

    public List<AdNode> selectFrontendContentByAD() {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(AD_CATEGORY_ID);
        List<TbContent> list = contentMapper.selectByExample(example);
        List<AdNode> adNodes = new ArrayList<>();
        for (TbContent tbContent : list) {
            AdNode node = new AdNode();
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setHref(tbContent.getUrl());
            node.setHeight(AD_HEIGHT);
            node.setWidth(AD_WIDTH);
            node.setHeightB(AD_HEIGHTB);
            node.setWidthB(AD_WIDTHB);
            adNodes.add(node);
        }
        return adNodes;
    }
}
