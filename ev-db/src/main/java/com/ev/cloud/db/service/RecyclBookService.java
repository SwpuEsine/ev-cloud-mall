package com.ev.cloud.db.service;

import com.ev.cloud.db.domain.RecyclBook;
import com.ev.cloud.db.mapper.RecyclBookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecyclBookService{

    @Resource
    private RecyclBookMapper recyclBookMapper;

    public int insert(RecyclBook pojo){
        return recyclBookMapper.insert(pojo);
    }

    public int insertSelective(RecyclBook pojo){
        return recyclBookMapper.insertSelective(pojo);
    }

    public int insertList(List<RecyclBook> pojos){
        return recyclBookMapper.insertList(pojos);
    }

    public int update(RecyclBook pojo){
        return recyclBookMapper.update(pojo);
    }

    public RecyclBook findByisbn(String isbn){
        return recyclBookMapper.findByisbn(isbn);
    }

    public RecyclBook findByid(Integer id){
        return recyclBookMapper.findByid(id);
    }
}
