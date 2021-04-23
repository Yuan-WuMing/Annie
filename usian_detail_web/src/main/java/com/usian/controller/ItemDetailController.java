package com.usian.controller;

import com.usian.feign.ItemServiceFeign;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemDesc;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamItem;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frontend/detail")
public class ItemDetailController {

    @Autowired
    private ItemServiceFeign itemServiceFeign;

    @RequestMapping("selectItemInfo")
    public Result selectItemInfo(Long itemId){
        TbItem tbItem = itemServiceFeign.selectItemInfo(itemId);
        if (tbItem!=null){
            return Result.ok(tbItem);
        }
        return Result.error("查不到，，，");
    }


    @RequestMapping("selectItemDescByItemId")
    public Result selectItemDescByItemId(Long itemId){
        TbItemDesc tbItem = itemServiceFeign.selectItemDescByItemId(itemId);
        if (tbItem!=null){
            return Result.ok(tbItem);
        }
        return Result.error("查不到，，，");
    }


    @RequestMapping("selectTbItemParamItemByItemId")
    public Result selectTbItemParamItemByItemId(Long itemId){
        TbItemParamItem tbItem = itemServiceFeign.selectTbItemParamItemByItemId(itemId);
        if (tbItem!=null){
            return Result.ok(tbItem);
        }
        return Result.error("查不到，，，");
    }
}
