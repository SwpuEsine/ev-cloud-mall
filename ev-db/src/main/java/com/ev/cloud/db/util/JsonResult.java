package com.ev.cloud.db.util;

import lombok.Data;

/**
 * @author
 * @create 2019-03-01 下午5:05
 **/
@Data
public class JsonResult<T> {
    //返回码
    private int code;
    // 信息
    private String msg;
    //数据
    private T data;


    public static  JsonResult ok(String msg){
        return ok(200,msg);
    }
    /**
     * 返回成功
     * @param code
     * @param msg
     * @return
     */
    public static JsonResult ok(int code,String msg){
        JsonResult JsonResult=new JsonResult();
        return JsonResult.ok(code,msg,null);
    }

    public static<T> JsonResult ok(T data){
        return ok(200,"success",data);
    }

    public static<T> JsonResult error(T data){
        return ok(500,"error",data);
    }
    /**
     * 返回成功带数据
     * @param code
     * @param msg
     * @return
     */
    public static<T> JsonResult ok(int code,String msg,T data){
        JsonResult<T> jsonResult=new JsonResult<>();
        jsonResult.setCode(code);
        jsonResult.setMsg(msg);
        jsonResult.setData(data);
        return jsonResult;
    }

    /**
     * 返回失败
     */
    public static JsonResult error(String messag) {
        return error(500, messag);
    }

    public static JsonResult error(int code, String message) {
        return ok(code, message);
    }
    /**
     * 返回失败
     */
    public static JsonResult error() {
        return error("操作失败");
    }

    /**
     * session过期
     * @param msg
     * @return
     */
    public static JsonResult unAuthorized(String msg) {

        return ok(401,msg);
    }

    public static JsonResult badArgument() {
        return error("参数错误!");
    }
}
