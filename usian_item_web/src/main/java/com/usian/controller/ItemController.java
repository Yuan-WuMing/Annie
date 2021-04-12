package com.usian.controller;

import com.usian.feign.ItemServiceFeign;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend")
public class ItemController {

    @Autowired
    private ItemServiceFeign itemServiceFeign;

    @RequestMapping("/item/selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "10")Integer rows){
        PageResult pageResult = itemServiceFeign.selectTbItemAllByPage(page,rows);
        if (pageResult.getResult()!=null && pageResult.getResult().size()>0){
            return Result.ok(pageResult);
        }
        return Result.error("查询无结果");
    }

    @RequestMapping("/itemCategory/selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(@RequestParam(defaultValue = "0")Long id){
        List<TbItemCat> list = itemServiceFeign.selectItemCategoryByParentId(id);
        if(list.size() > 0 && list!=null){
            return Result.ok(list);
        }
        return Result.error("查询无结果");
    }

    @RequestMapping("/item/insertTbItem")
    public Result insertTbItem(TbItem tbItem,String desc,String itemParams){
        Integer insertTbItemNum = itemServiceFeign.insertTbItem(tbItem,desc,itemParams);
        if (insertTbItemNum==3){
            return Result.ok();
        }
        return Result.error("添加失败");
    }

    @RequestMapping("/item/preUpdateItem")
    public Result preUpdateItem(Long itemId){
        Map<String,Object> map = itemServiceFeign.preUpdateItem(itemId);
        if (map.size()>0){
            return Result.ok(map);
        }
        return Result.error("回显错误");
    }

    @RequestMapping("item/deleteItemById")
    public Result deleteItemById(Long itemId){
        /*itemServiceFeign.deleteItemById(itemId);*/
        return Result.error("删除错误");
    }
}
