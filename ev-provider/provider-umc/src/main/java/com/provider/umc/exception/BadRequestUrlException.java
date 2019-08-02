package com.provider.umc.exception;


import com.ev.common.base.enums.ResultErrorEnum;


public class BadRequestUrlException extends BaseException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3908881775148165922L;

    /**
     * 错误请求url异常构造方法
     * @param resultErrorEnum 异常错误枚举类
     */
    public BadRequestUrlException(final ResultErrorEnum resultErrorEnum) {
        super(resultErrorEnum);
    }

}
