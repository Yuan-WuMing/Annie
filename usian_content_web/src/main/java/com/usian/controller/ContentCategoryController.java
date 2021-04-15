package com.usian.controller;

import com.usian.feign.ContentServiceFeign;
import com.usian.pojo.TbContentCategory;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("content")
public class ContentCategoryController {

    @Autowired
    private ContentServiceFeign contentServiceFeign;

    @RequestMapping("selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(@RequestParam(defaultValue = "0") Long id){
        List<TbContentCategory> list =contentServiceFeign.selectContentCategoryByParentId(id);
        if (list.size()>0 && list!=null){
            return Result.ok(list);
        }
        return Result.error("查询没结果");
    }

    @RequestMapping("/insertContentCategory")
    public Result insertContentCategory(TbContentCategory tbContentCategory){
        Integer contentCategoryNum = contentServiceFeign.insertContentCategory(tbContentCategory);
        if(contentCategoryNum==1){
            return Result.ok();
        }
        return Result.error("添加失败");
    }

    @RequestMapping("deleteContentCategoryById")
    public Result deleteContentCategoryById(Long categoryId){
        Integer i = contentServiceFeign.deleteContentCategoryById(categoryId);
        if (i==1){
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }
}
