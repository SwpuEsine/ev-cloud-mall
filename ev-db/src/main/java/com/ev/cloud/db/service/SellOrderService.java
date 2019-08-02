package com.ev.cloud.db.service;


import com.ev.cloud.db.dto.SellOrder;
import com.ev.cloud.db.mapper.SellOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SellOrderService{

    @Resource
    private SellOrderMapper sellOrderDao;

    public int insert(SellOrder pojo){
        return sellOrderDao.insert(pojo);
    }

    public int insertSelective(SellOrder pojo){
        return sellOrderDao.insertSelective(pojo);
    }

    public int insertList(List<SellOrder> pojos){
        return sellOrderDao.insertList(pojos);
    }

    public int update(SellOrder pojo){
        return sellOrderDao.update(pojo);
    }
}
