/*
package com.provider.order.web;


import com.ev.cloud.db.service.WxOrderService;
import com.ev.cloud.db.util.JsonResult;
import com.ev.common.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/wx/order")
@Api(tags = "订单管理")
@Validated
public class WxOrderController extends BaseController{

    @Autowired
    private WxOrderService wxOrderService;

    */
/**
     * 订单列表
     *
     * @param
     * @param showType 订单信息
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表a
     *//*

    @GetMapping("list")
    @ApiOperation(value = "订单列表",notes = "获取订单列表")
    public JsonResult list(
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.list(showType, page, limit, userId);
    }

    */
/**
     * 订单详情
     *
     * @param
     * @param orderId 订单ID
     * @return 订单详情
     *//*

    @GetMapping("detail")
    @ApiOperation(value = "订单详情",notes = "获取订单详情")
    public JsonResult detail(@NotNull Integer orderId) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.detail(orderId, userId);
    }

    */
/**
     * 提交订单
     *
     * @param
     * @param body   订单信息，{ cartId：xxx, addressId: xxx, couponId: xxx, message: xxx, grouponRulesId: xxx,  grouponLinkId: xxx}
     * @return 提交订单操作结果
     *//*

    @PostMapping("submit")
    @ApiOperation(value = "提交订单",notes = "提交订单")
    public JsonResult submit( @RequestBody String body) throws InterruptedException {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        Thread.sleep(7000);
        return wxOrderService.submit( body, userId);
    }

    */
/**
     * 取消订单
     *
     * @param
     * @param body   订单信息，{ orderId：xxx }
     * @return 取消订单操作结果
     *//*

    @PostMapping("cancel")
    @ApiOperation(value = "取消订单",notes = "取消订单")
    public JsonResult cancel( @RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        throw new RuntimeException("我就是要出错");
        //return wxOrderService.cancel( body, userId);
    }

    */
/**
     * 付款订单的预支付会话标识
     *
     * @param
     * @param body   订单信息，{ orderId：xxx }
     * @return 支付订单ID
     *//*

    @PostMapping("prepay")
    @ApiOperation(value = "预支付",notes = "预支付")
    public JsonResult prepay(@RequestBody String body, HttpServletRequest request) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.prepay( body, request, userId);
    }

    */
/**
     * 微信付款成功或失败回调接口
     * <p>
     *  TODO
     *  注意，这里pay-notify是示例地址，建议开发者应该设立一个隐蔽的回调地址
     *
     * @param request 请求内容
     * @param response 响应内容
     * @return 操作结果
     *//*

    @PostMapping("pay-notify")
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        return wxOrderService.payNotify(request, response);
    }

    */
/**
     * 订单申请退款
     *
     * @param
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     *//*

    @PostMapping("refund")
    @ApiOperation(value = "订单申请退款",notes = "订单申请退款")
    public JsonResult refund( @RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.refund( body, userId);
    }

    */
/**
     * 确认收货
     *
     * @param
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     *//*

    @PostMapping("confirm")
    @ApiOperation(value = "确认收货",notes = "确认收货")
    public Object confirm( @RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.confirm( body, userId);
    }

    */
/**
     * 删除订单
     *
     * @param
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     *//*

    @PostMapping("delete")
    @ApiOperation(value = "删除订单",notes = "删除订单")
    public Object delete( @RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.delete( body, userId);
    }

    */
/**
     * 待评价订单商品信息
     *
     * @param
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 待评价订单商品信息
     *//*

    @GetMapping("goods")
    @ApiOperation(value = "待评价订单商品信息",notes = "待评价订单商品信息")
    public Object goods(
                        @NotNull Integer orderId,
                        @NotNull Integer goodsId) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登陆");
        }
        return wxOrderService.goods(orderId, goodsId);
    }

    */
/**
     * 评价订单商品
     *
     * @param
     * @param body   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     *//*

    @PostMapping("comment")
    @ApiOperation(value = "商品评价",notes = "商品评价")
    public Object comment(@RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return wxOrderService.comment(body, userId);
    }

}*/
