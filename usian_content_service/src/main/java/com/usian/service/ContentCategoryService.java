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
//        Date date = new Date();
//        tbContentCategory.setCreated(date);
//        tbContentCategory.setUpdated(date);
//        tbContentCategory.setIsParent(false);
//        tbContentCategory.setSortOrder(1);
//        tbContentCategory.setStatus(1);
//        int i = contentCategoryMapper.insert(tbContentCategory);
//
//        TbContentCategory tbContentCategory1 = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
//
//        if (tbContentCategory1.getIsParent()==true){
//            tbContentCategory.setIsParent(true);
//            tbContentCategory.setUpdated(date);
//            contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
//        }
//        return i;


        //1、添加内容分类
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        Integer contentCategoryNum =
                this.contentCategoryMapper.insert(tbContentCategory);
        //2、如果他爹不是爹，要把他爹改成爹
        //2.1、查询当前新节点的父节点
        TbContentCategory contentCategory =
                this.contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        //2.2、判断当前父节点是否是叶子节点
        if (!contentCategory.getIsParent()) {
            contentCategory.setIsParent(true);
            contentCategory.setUpdated(new Date());
            this.contentCategoryMapper.updateByPrimaryKey(contentCategory);
        }
        return contentCategoryNum;


        /*Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        return contentCategoryMapper.insert(tbContentCategory);*/
    }

    public Integer deleteContentCategoryById(Long categoryId) {
        /*TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(categoryId);
        //父节点 不允许删除
        if(tbContentCategory.getIsParent()==true){
            return 0;
        }
        //不是父节点
        contentCategoryMapper.deleteByPrimaryKey(categoryId);
        //当前节点的兄弟节点
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(tbContentCategory.getParentId());
        List<TbContentCategory> tbContentCategoryList = contentCategoryMapper.selectByExample(tbContentCategoryExample);
        //删除之后如果父节点没有孩子，则修改isParent为false
        if(tbContentCategoryList.size()==0){
            TbContentCategory parenttbContentCategory = new TbContentCategory();
            parenttbContentCategory.setId(tbContentCategory.getParentId());
            parenttbContentCategory.setIsParent(false);
            parenttbContentCategory.setUpdated(new Date());
            this.contentCategoryMapper.updateByPrimaryKeySelective(
                    parenttbContentCategory);
        }
        return null;*/

        //先根据id查出这条对象
        //判断当前分类是否为根，。。。。。。
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(categoryId);

        if (tbContentCategory.getParentId()==0){
            //当前分类为庚 所以不能删除
            return 0;
        }


        return null;
    }
}
