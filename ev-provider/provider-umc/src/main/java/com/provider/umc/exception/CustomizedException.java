package com.provider.umc.exception;


import com.ev.common.base.enums.ResultErrorEnum;

/**
 * 项目名称: ldap-service <br>
 * 包: com.migu.tsg.microservice.atomicservice.ldap.exception <br>
 * 类名称: CustomizedException.java <br>
 * 类描述: 自定义通用异常 <br>
 * 创建人: WangSheng <br>
 * 创建时间: 2017年8月29日上午9:36:10 <br>
 * 版本: v1.0
 */
public class CustomizedException extends BaseException {

    private static final long serialVersionUID = -2314885664539531267L;

    /**
     * 自定义通用异常构造方法
     * @param message 错误信息
     */
    public CustomizedException(final String message) {
        super(message);
    }

    /**
     * 自定义通用异常构造方法
     * @param cause 异常对象
     * 
     */
    public CustomizedException(Throwable cause) {
        super(cause);
    }

    /**
     * 自定义通用异常构造方法
     * @param message 错误信息
     * @param cause 异常对象
     */
    public CustomizedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 自定义通用异常构造方法
     * @param resultErrorEnum 错误结果枚举类
     */
    public CustomizedException(final ResultErrorEnum resultErrorEnum) {
        super(resultErrorEnum);
    }
    
    /**
     * 自定义通用异常构造方法
     * @param resultErrorEnum 错误结果枚举类
     * @param cause 异常对象
     */
    public CustomizedException(final ResultErrorEnum resultErrorEnum, final Throwable cause) {
        super(resultErrorEnum, cause);
    }

}
