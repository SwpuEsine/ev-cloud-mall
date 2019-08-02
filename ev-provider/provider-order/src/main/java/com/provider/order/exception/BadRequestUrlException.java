package com.provider.order.exception;


import com.ev.common.base.enums.ResultErrorEnum;

/**
 * 项目名称: ldap-service <br>
 * 包: com.migu.tsg.microservice.atomicservice.ldap.exception <br>
 * 类名称: BadRequestUrlException.java <br>
 * 类描述: 请求URL无效 <br>
 * 创建人: WangSheng <br>
 * 创建时间: 2017年7月28日上午9:09:23 <br>
 * 版本: v1.0
 */
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
