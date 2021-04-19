package com.usian.controller;

import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.service.ItemService;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("service")
public class ItemController {

   @Autowired
   private ItemService itemService;

   @RequestMapping("item/selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(Integer page,Integer rows){
       return itemService.selectTbItemAllByPage(page,rows);
   }

   @RequestMapping("itemCategory/selectItemCategoryByParentId")
   public List<TbItemCat> selectItemCategoryByParentId(@RequestParam Long id){
      return itemService.selectItemCategoryByParentId(id);
   }

   @RequestMapping("item/insertTbItem")
   public Integer insertTbItem(@RequestBody TbItem tbItem,String desc,String itemParams){
      return itemService.insertTbItem(tbItem,desc,itemParams);
   }

   @RequestMapping("item/preUpdateItem")
   public Map<String, Object> preUpdateItem(Long itemId){
      return itemService.preUpdateItem(itemId);
   }

   @RequestMapping("item/updateTbItem")
   public Integer updateTbItem(@RequestBody TbItem tbItem,String desc,String itemParams){
      return itemService.updateTbItem(tbItem,desc,itemParams);
   }

   @RequestMapping("item/deleteItemById")
   public Integer deleteItemById(Long itemId){
      Integer count = itemService.deleteItemById(itemId);
      return count;
   }


}
