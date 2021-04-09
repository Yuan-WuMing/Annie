package com.usian.controller;

import com.usian.pojo.TbItemCat;
import com.usian.service.ItemService;
import com.usian.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
