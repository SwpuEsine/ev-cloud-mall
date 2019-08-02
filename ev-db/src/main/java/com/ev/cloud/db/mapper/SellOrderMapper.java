package com.ev.cloud.db.mapper;

import com.ev.cloud.db.dto.SellOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SellOrderMapper {
    int insert(@Param("pojo") SellOrder pojo);

    int insertSelective(@Param("pojo") SellOrder pojo);

    int insertList(@Param("pojos") List<SellOrder> pojo);

    int update(@Param("pojo") SellOrder pojo);
}
