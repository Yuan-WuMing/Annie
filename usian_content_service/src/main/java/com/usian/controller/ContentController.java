package com.usian.controller;

import com.usian.pojo.TbContent;
import com.usian.service.ContentService;
import com.usian.utils.AdNode;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("service/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("selectTbContentAllByCategoryId")
    PageResult selectTbContentAllByCategoryId(@RequestParam Long categoryId){
        return contentService.selectTbContentAllByCategoryId(categoryId);
    }

    @RequestMapping("selectFrontendContentByAD")
    List<AdNode> selectFrontendContentByAD(){
        return contentService.selectFrontendContentByAD();
    }


}
