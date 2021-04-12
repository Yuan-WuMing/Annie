package com.usian.service;

import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamService {

    @Autowired
    private TbItemParamMapper mapper;



    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = mapper.selectByExampleWithBLOBs(example);
        if (list.size()>0 && list!=null){
            return list.get(0);
        }
        return null;
    }
}
