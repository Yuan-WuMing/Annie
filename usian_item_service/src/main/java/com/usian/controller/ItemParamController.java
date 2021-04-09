package com.usian.controller;

import com.usian.pojo.TbItemParam;
import com.usian.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/itemParam/selectItemParamByItemCatId")
    public TbItemParam selectItemParamByItemCatId(Long itemCatId){
        return itemParamService.selectItemParamByItemCatId(itemCatId);

    }


}
