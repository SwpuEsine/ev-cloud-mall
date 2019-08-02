package com.ev.cloud.db.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author
 * @create 2019-07-03 5:49 AM
 **/
@Data
public class RecyclBook {
    private Integer id;
    private String bookName;
    private String isbn;
    private String author;
    private String desc;
    //收购最高价格
    private BigDecimal maxPrice;
    private BigDecimal commonPrice;
    private String picUrl;
    //运费id
    private Integer freightId;

}
