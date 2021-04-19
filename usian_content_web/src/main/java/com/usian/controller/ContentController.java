package com.usian.controller;

import com.usian.feign.ContentServiceFeign;
import com.usian.pojo.TbContent;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentServiceFeign contentServiceFeign;

    @RequestMapping("selectTbContentAllByCategoryId")
    public Result selectContentCategoryByParentId(Long categoryId){
        PageResult pageResult = contentServiceFeign.selectTbContentAllByCategoryId(categoryId);
        if (pageResult.getResult()!=null){
            return Result.ok(pageResult);
        }
        return Result.error("查询无结果");
    }
}
