package com.usian.controller;

import com.usian.mapper.TbItemMapper;
import com.usian.pojo.TbItem;
import com.usian.service.ItemService;
import com.usian.utils.PageResult;
import com.usian.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backend/item")
public class ItemController {

   @Autowired
   private ItemService itemService;

   @RequestMapping("selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(Integer page,Integer rows){
       return itemService.selectTbItemAllByPage(page,rows);
   }
}
