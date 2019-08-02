package com.provider.order.exception;

import com.ev.common.base.enums.ResultErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目名称: ldap-service <br>
 * 包: com.migu.tsg.microservice.atomicservice.ldap.exception <br>
 * 类名称: BaseExistException.java <br>
 * 类描述: 基础异常类 <br>
 * 创建人: WangSheng <br>
 * 创建时间: 2017年7月26日下午3:33:46 <br>
 * 版本: v1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5408878145521704434L;

    /**
     * 供内部排查使用
     */
    protected String source;

    /**
     * 错误码
     */
    protected String code;

    /**
     * HTTP 状态码
     */
    protected int httpCode;
    
    /**
     * 错误类型
     */
    protected String type;

    /**
     * 构造方法
     * @param message 错误信息
     */
    public BaseException(final String message) {
        super(message);
    }

    /**
     * 构造方法
     * @param cause 异常对象
     * 
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造方法
     * @param message 错误信息
     * @param cause 异常对象
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法
     * @param resultErrorEnum resultErrorEnum
     */
    public BaseException(final ResultErrorEnum resultErrorEnum) {
        super(resultErrorEnum.getMessage());
        this.source = resultErrorEnum.getSource();
        this.code = resultErrorEnum.getCode();
        this.httpCode = resultErrorEnum.getHttpCode();
        this.type = resultErrorEnum.getType();
    }
    
    /**
     * 构造方法
     * @param resultErrorEnum resultErrorEnum
     * @param cause cause
     */
    public BaseException(final ResultErrorEnum resultErrorEnum, final Throwable cause) {
        super(resultErrorEnum.getMessage(), cause);
        this.source = resultErrorEnum.getSource();
        this.code = resultErrorEnum.getCode();
        this.httpCode = resultErrorEnum.getHttpCode();
        this.type = resultErrorEnum.getType();
    }

    /**
     * 构造方法
     * @param resultErrorEnum resultErrorEnum
     * @param message message
     */
    public BaseException(final ResultErrorEnum resultErrorEnum, final String message) {
        super(message);
        this.source = resultErrorEnum.getSource();
        this.code = resultErrorEnum.getCode();
        this.httpCode = resultErrorEnum.getHttpCode();
        this.type = resultErrorEnum.getType();
    }

}
