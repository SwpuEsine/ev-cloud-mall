package com.ev.cloud.db.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class SellOrder {
    private Integer id;
    private Integer userId;
    @NotNull(message="书籍不能为空")
    private Integer recycleBookId;
    @NotNull(message="数量不能为空")
    private Integer number;

    @NotNull(message="手机号不能为空")
    @Pattern(regexp = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))",message = "手机号格式错误")
    private String phone;
    @NotEmpty(message="地址不能为空")
    private String region;
    @NotEmpty(message="详细地址不能为空")
    private String addressDetail;
    //0未完成 1已完成
    private Short status;
    //预约上门时间
    @Future(message = "预约时间有问题")
    @NotNull(message = "预约时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointTime;
    private Date createTime;
    //预计单价
    private BigDecimal reservePrice;
    private BigDecimal actualPrice;
    @NotEmpty(message = "用户名不能为空")
    private String userName;

}
