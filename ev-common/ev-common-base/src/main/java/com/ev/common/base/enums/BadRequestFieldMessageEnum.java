/**
 * 
 */
package com.ev.common.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称: ldap-service <br>
 * 包: com.migu.tsg.microservice.atomicservice.ldap.enums <br>
 * 类名称: BadRequestFieldDescribeEnum.java <br>
 * 类描述: 请求字段不合法的异常之无效原因的提示信息枚举类 <br>
 * 创建人: WangSheng <br>
 * 创建时间: 2017年8月29日上午10:17:36 <br>
 * 版本: v1.0
 */
@AllArgsConstructor
public enum BadRequestFieldMessageEnum {

    /**
     * 根账号(空间名称)不能为空
     */
    NAMESPACE_NAME_CANNOT_BE_EMPTY("Namespace name cannot be empty"),

    /**
     * 用户名称不能为空
     */
    USER_NAME_CANNOT_BE_EMPTY("User name cannot be empty"),

    /**
     * 用户邮箱不能为空
     */
    USER_MAIL_CANNOT_BE_EMPTY("User mail cannot be empty"),

    /**
     * USERNAME不合法的/无效的
     */
    USER_NAME_INVALID("Required username {} not valid, pattern = ^[a-zA-Z]\\w{5,20}$"),

    /**
     * PASSWORD不合法的/无效的
     */
    USER_PASSWORD_INVALID("Required password {} not valid, pattern = ^[\\s\\S]{2,20}$"),

    /**
     * EMAIL不合法的/无效的
     */
    USER_EMAIL_INVALID("Required email {} not valid"),

    /**
     * MOBILE不合法的/无效的
     */
    USER_MOBILE_INVALID("Required mobile {} not valid"),

    /**
     * LDAP之User已经存在
     */
    USER_NAME_ALREADY_EXIST("Ldap user {} already exist"),

    /**
     * LDAP之User所属项目不存在
     */
    USER_PROJECT_NOT_EXIST("User project {} not exist"),

    /**
     * 用户旧PASSWORD不正确
     */
    USER_OLD_PASSWORD_INCORRECT("User {} in {} old password {} is incorrect"),

    /**
     * 用户名称已经存在于PAYLOAD中
     */
    USER_NAME_ALREADY_EXIST_IN_PAYLOAD("User name {} already exist in payload"),

    /**
     * LDAP之User不存在
     */
    LDAP_USER_NOT_EXIST("Ldap user {} in {} does not exist"),

    /**
     * 新增LDAP用户失败
     */
    INSERT_LDAP_USER_FAILED("Add ldap user {} in {} fail"),

    /**
     * 至少新增一个LDAP用户
     */
    INSERT_LEAST_ONE_LDAP_USER("Add at least one user in {}"),

    /**
     * 更新LDAP用户失败
     */
    UPDATE_LDAP_USER_FAILED("Modify ldap user {} in {} failed"),

    /**
     * 删除LDAP用户失败
     */
    DELETE_LDAP_USER_FAILED("Remove ldap user {} in {} fail"),

    /**
     * 校验LDAP用户旧密码失败
     */
    VERIFY_LDAP_USER_PASSWORD_FAILED("Verify ldap user {} in {} old password {} failed"),

    /**
     * 映射LDAP用户属性(EMPLOYEETYPE)失败
     */
    MAP_LDAP_USER_EMPLOYEETYPE_FAILED("Map ldap user attribute {} fail, Because it can't be empty"),

    /**
     * 映射LDAP用户属性(EMPLOYEETYPE)失败
     */
    MAP_LDAP_USER_EMPLOYEETYPE_FAILED2("Map ldap user attribute {} : {} fail, No user of this type"),

    /**
     * 映射LDAP用户属性(USERPASSWORD)失败
     */
    MAP_LDAP_USER_USERPASSWORD_FAILED(
            "Map ldap user attribute {} : {} fail, Character set {} is not supported"),

    /**
     * 映射LDAP用户属性(JPEGPHOTO)失败
     */
    MAP_LDAP_USER_JPEGPHOTO_FAILED("Map ldap user attribute {} : {} fail, Character set {} is not supported"),

    ;

    @Getter
    private String message;

    /**
     * 动态描述信息
     * @param enumm 枚举对象
     * @param name 动态名称
     * @return 信息描述
     */
    public static String dynamicMessage(BadRequestFieldMessageEnum enumm, String... names) {
        String message = enumm.getMessage();
        for (String name : names) {
            message = Pattern.compile("\\{\\}").matcher(message)
                    .replaceFirst(Matcher.quoteReplacement(StringUtils.trimToEmpty(name)));
        }
        return Pattern.compile("\\s{2,}").matcher(message).replaceAll(" ");
    }

}
