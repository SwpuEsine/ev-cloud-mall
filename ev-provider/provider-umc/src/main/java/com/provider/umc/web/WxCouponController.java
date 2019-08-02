package com.provider.umc.web;


import com.ev.cloud.db.domain.LitemallCart;
import com.ev.cloud.db.domain.LitemallCoupon;
import com.ev.cloud.db.domain.LitemallCouponUser;
import com.ev.cloud.db.domain.LitemallGrouponRules;
import com.ev.cloud.db.service.*;
import com.ev.cloud.db.util.CouponConstant;
import com.ev.common.base.dto.JsonResult;
import com.ev.common.base.validator.Order;
import com.ev.common.base.validator.Sort;
import com.ev.common.utils.JacksonUtil;
import com.provider.umc.vo.CouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券服务
 */
@RestController
@RequestMapping("/wx/user/coupon")
@Validated
public class WxCouponController extends BaseController {

    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private LitemallGrouponRulesService grouponRulesService;
    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private CouponVerifyService couponVerifyService;

    /**
     * 优惠券列表
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("list")
    public JsonResult list(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {

        List<LitemallCoupon> couponList = couponService.queryList(page, limit, sort, order);
        int total = couponService.queryTotal();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", couponList);
        data.put("count", total);
        return JsonResult.ok(data);
    }

    /**
     * 个人优惠券列表
     *
     * @param
     * @param status
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("mylist")
    public JsonResult mylist(
                       @NotNull Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登录");
        }

        List<LitemallCouponUser> couponUserList = couponUserService.queryList(userId, null, status, page, limit, sort, order);
        List<CouponVo> couponVoList = change(couponUserList);
        int total = couponService.queryTotal();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", couponVoList);
        data.put("count", total);
        return JsonResult.ok(data);
    }

    private List<CouponVo> change(List<LitemallCouponUser> couponList) {
        List<CouponVo> couponVoList = new ArrayList<>(couponList.size());
        for(LitemallCouponUser couponUser : couponList){
            Integer couponId = couponUser.getCouponId();
            LitemallCoupon coupon = couponService.findById(couponId);
            CouponVo couponVo = new CouponVo();
            couponVo.setId(coupon.getId());
            couponVo.setName(coupon.getName());
            couponVo.setDesc(coupon.getDesc());
            couponVo.setTag(coupon.getTag());
            couponVo.setMin(coupon.getMin().toPlainString());
            couponVo.setDiscount(coupon.getDiscount().toPlainString());
            couponVo.setStartTime(couponUser.getStartTime());
            couponVo.setEndTime(couponUser.getEndTime());

            couponVoList.add(couponVo);
        }

        return couponVoList;
    }


    /**
     * 当前购物车下单商品订单可用优惠券
     *
     * @param
     * @param cartId
     * @param grouponRulesId
     * @return
     */
    @GetMapping("selectlist")
    public JsonResult selectlist( Integer cartId, Integer grouponRulesId) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登录");
        }

        // 团购优惠
        BigDecimal grouponPrice = new BigDecimal(0.00);
        LitemallGrouponRules grouponRules = grouponRulesService.queryById(grouponRulesId);
        if (grouponRules != null) {
            grouponPrice = grouponRules.getDiscount();
        }

        // 商品价格
        List<LitemallCart> checkedGoodsList = null;
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            if (cart == null) {
                return JsonResult.error("参数错误");
            }
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (LitemallCart cart : checkedGoodsList) {
            //  只有当团购规格商品ID符合才进行团购优惠
            if (grouponRules != null && grouponRules.getGoodsId().equals(cart.getGoodsId())) {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
            } else {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }

        // 计算优惠券可用情况
        List<LitemallCouponUser> couponUserList = couponUserService.queryAll(userId);
        List<LitemallCouponUser> availableCouponUserList = new ArrayList<>(couponUserList.size());
        for (LitemallCouponUser couponUser : couponUserList) {
            LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), checkedGoodsPrice);
            if (coupon == null) {
                continue;
            }
            availableCouponUserList.add(couponUser);
        }

        List<CouponVo> couponVoList = change(availableCouponUserList);

        return JsonResult.ok(couponVoList);
    }

    /**
     * 优惠券领取
     *
     * @param
     * @param body 请求内容， { couponId: xxx }
     * @return 操作结果
     */
    @PostMapping("receive")
    public JsonResult receive(@RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登录");
        }

        Integer couponId = JacksonUtil.parseInteger(body, "couponId");
        if(couponId == null){
            return JsonResult.error("参数错误");
        }

        LitemallCoupon coupon = couponService.findById(couponId);
        if(coupon == null){
            return JsonResult.error("参数错误");
        }

        // 当前已领取数量和总数量比较
        Integer total = coupon.getTotal();
        Integer totalCoupons = couponUserService.countCoupon(couponId);
        if((total != 0) && (totalCoupons >= total)){
           return JsonResult.error("优惠券已领完");
        }

        // 当前用户已领取数量和用户限领数量比较
        Integer limit = coupon.getLimit().intValue();
        Integer userCounpons = couponUserService.countUserAndCoupon(userId, couponId);
        if((limit != 0) && (userCounpons >= limit)){
            return JsonResult.error("优惠券已经领取过");
        }

        // 优惠券分发类型
        // 例如注册赠券类型的优惠券不能领取
        Short type = coupon.getType();
        if(type.equals(CouponConstant.TYPE_REGISTER)){
            return JsonResult.error("新用户优惠券自动发送");
        }
        else if(type.equals(CouponConstant.TYPE_CODE)){
            return JsonResult.error("优惠券只能兑换");
        }
        else if(!type.equals(CouponConstant.TYPE_COMMON)){
            return JsonResult.error("优惠券类型不支持");
        }

        // 优惠券状态，已下架或者过期不能领取
        Short status = coupon.getStatus();
        if(status.equals(CouponConstant.STATUS_OUT)){
            return JsonResult.error("优惠券已领完");
        }
        else if(status.equals(CouponConstant.STATUS_EXPIRED)){
            return JsonResult.error("优惠券已经过期");
        }

        // 用户领券记录
        LitemallCouponUser couponUser = new LitemallCouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setUserId(userId);
        Short timeType = coupon.getTimeType();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            couponUser.setStartTime(now);
            couponUser.setEndTime(now.plusDays(coupon.getDays()));
        }
        couponUserService.add(couponUser);

        return JsonResult.ok("领取成功");
    }

    /**
     * 优惠券兑换
     *
     * @param
     * @param body 请求内容， { code: xxx }
     * @return 操作结果
     */
    @PostMapping("exchange")
    public JsonResult exchange(@RequestBody String body) {
        Integer userId = getUserId();
        if (userId == null) {
            return JsonResult.unAuthorized("未登录");
        }

        String code = JacksonUtil.parseString(body, "code");
        if(code == null){
            return JsonResult.error("参数错误");
        }

        LitemallCoupon coupon = couponService.findByCode(code);
        if(coupon == null){
            return JsonResult.error("优惠券不正确");
        }
        Integer couponId = coupon.getId();

        // 当前已领取数量和总数量比较
        Integer total = coupon.getTotal();
        Integer totalCoupons = couponUserService.countCoupon(couponId);
        if((total != 0) && (totalCoupons >= total)){
            return JsonResult.error("优惠券已兑换");
        }

        // 当前用户已领取数量和用户限领数量比较
        Integer limit = coupon.getLimit().intValue();
        Integer userCounpons = couponUserService.countUserAndCoupon(userId, couponId);
        if((limit != 0) && (userCounpons >= limit)){
            return JsonResult.error("优惠券已兑换");
        }

        // 优惠券分发类型
        // 例如注册赠券类型的优惠券不能领取
        Short type = coupon.getType();
        if(type.equals(CouponConstant.TYPE_REGISTER)){
            return JsonResult.error("新用户优惠券自动发送");
        }
        else if(type.equals(CouponConstant.TYPE_COMMON)){
            return JsonResult.error("优惠券不正确");
        }
        else if(!type.equals(CouponConstant.TYPE_CODE)){
            return JsonResult.error("优惠券只能领取，不能兑换");
        }

        // 优惠券状态，已下架或者过期不能领取
        Short status = coupon.getStatus();
        if(status.equals(CouponConstant.STATUS_OUT)){
            return JsonResult.error("优惠券已兑换");
        }
        else if(status.equals(CouponConstant.STATUS_EXPIRED)){
            return JsonResult.error("优惠券已经过期");
        }

        // 用户领券记录
        LitemallCouponUser couponUser = new LitemallCouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setUserId(userId);
        Short timeType = coupon.getTimeType();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            couponUser.setStartTime(now);
            couponUser.setEndTime(now.plusDays(coupon.getDays()));
        }
        couponUserService.add(couponUser);

        return JsonResult.ok("兑换成功");
    }
}