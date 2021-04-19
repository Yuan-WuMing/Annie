package com.usian.controller;

import com.usian.feign.ContentServiceFeign;
import com.usian.utils.AdNode;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("frontend")
public class ContentController {

    @Autowired
    private ContentServiceFeign contentServiceFeign;

    @RequestMapping("content/selectFrontendContentByAD")
    public Result selectFrontendContentByAD(){
        List<AdNode> list =contentServiceFeign.selectFrontendContentByAD();
        if (list.size()>0){
            return Result.ok(list);
        }
        return Result.error("查询出错");
    }
}
