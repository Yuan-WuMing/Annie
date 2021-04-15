package com.usian.service;

import com.usian.mapper.TbContentCategoryMapper;
import com.usian.pojo.TbContentCategory;
import com.usian.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;


    public List<TbContentCategory> selectContentCategoryByParentId(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        criteria.andStatusEqualTo(1);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        return list;
    }

    public Integer insertContentCategory(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        int i = contentCategoryMapper.insert(tbContentCategory);

        TbContentCategory tbContentCategory1 = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());

        if (tbContentCategory1.getIsParent()){
            tbContentCategory.setIsParent(true);
            tbContentCategory.setUpdated(date);
            contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        }
        return i;
    }

    public Integer deleteContentCategoryById(Long categoryId) {
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(categoryId);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(tbContentCategory.getParentId());
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list.size()>0 && list!=null){
            tbContentCategory.setStatus(2);
            int i = contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
            return i;
        }
        return null;
    }
}
