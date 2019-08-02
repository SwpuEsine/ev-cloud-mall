package com.ev.cloud.db.mapper;

import com.ev.cloud.db.domain.RecyclBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecyclBookMapper {
    int insert(@Param("pojo") RecyclBook pojo);

    int insertSelective(@Param("pojo") RecyclBook pojo);

    int insertList(@Param("pojos") List<RecyclBook> pojo);

    int update(@Param("pojo") RecyclBook pojo);

    RecyclBook findByisbn(@Param("isbn") String isbn);

    RecyclBook findByid(@Param("id") Integer id);


}
