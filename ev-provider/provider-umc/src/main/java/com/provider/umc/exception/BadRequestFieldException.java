package com.provider.umc.exception;


import com.ev.common.base.enums.ResultErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestFieldException extends BaseException {

    private static final long serialVersionUID = 5516820019652198908L;

    /**
     * 无效的字段名
     */
    private String fieldName;

    /**
     * 无效原因的提示信息
     */
    private List<String> tipInfo;

    /**
     * 错误字段异常
     * @param resultErrorEnum resultErrorEnum
     * @param fieldName 字段名称
     * @param tipInf tipInfo
     */
    public BadRequestFieldException(final ResultErrorEnum resultErrorEnum, final String fieldName,
                                    final String[] tipInfo) {
        super(resultErrorEnum);
        this.fieldName = fieldName;
        this.tipInfo = Arrays.asList(tipInfo);
    }

    /**
     * 错误字段异常
     * @param resultErrorEnum resultErrorEnum
     * @param fieldName 字段名称
     * @param tipInf tipInfo
     * @param cause 异常对象
     */
    public BadRequestFieldException(final ResultErrorEnum resultErrorEnum, final String fieldName,
            final String[] tipInfo, final Throwable cause) {
        super(resultErrorEnum, cause);
        this.fieldName = fieldName;
        this.tipInfo = Arrays.asList(tipInfo);
    }

}
