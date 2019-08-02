//package com.provider.umc.web;
//
//
//import com.ev.cloud.db.domain.*;
//import com.ev.cloud.db.service.*;
//import com.ev.common.base.constant.SystemConfig;
//import com.ev.common.base.dto.JsonResult;
//import com.ev.common.utils.JacksonUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 用户购物车服务
// */
//@RestController
//@RequestMapping("/wx/user/cart")
//@Api(tags = "购物车管理")
//@Validated
//public class WxCartController extends BaseController implements WxCartClient{
//
//    @Autowired
//    private LitemallCartService cartService;
//    @Autowired
//    private LitemallGoodsService goodsService;
//    @Autowired
//    private LitemallGoodsProductService productService;
//    @Autowired
//    private LitemallAddressService addressService;
//    @Autowired
//    private LitemallGrouponRulesService grouponRulesService;
//    @Autowired
//    private LitemallCouponService couponService;
//    @Autowired
//    private LitemallCouponUserService couponUserService;
//    @Autowired
//    private CouponVerifyService couponVerifyService;
//
//    @Autowired
//    private LitemallOrderService orderService;
//    /**
//     * 用户购物车信息
//     *
//     * @param
//     * @return 用户购物车信息
//     */
//    @GetMapping("index")
//    @ApiOperation(value = "用户购物车信息",notes = "用户购物车信息")
//    public JsonResult index() {
//        Integer userId = getUserId();
//        if (userId == null) {
//            return JsonResult.unAuthorized("未登陆");
//        }
//        List<LitemallCart> cartList = cartService.queryByUid(userId);
//        Integer goodsCount = 0;
//        BigDecimal goodsAmount = new BigDecimal(0.00);
//        Integer checkedGoodsCount = 0;
//        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
//        for (LitemallCart cart : cartList) {
//            goodsCount += cart.getNumber();
//            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
//            if (cart.getChecked()) {
//                checkedGoodsCount += cart.getNumber();
//                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
//            }
//        }
//        Map<String, Object> cartTotal = new HashMap<>();
//        cartTotal.put("goodsCount", goodsCount);
//        cartTotal.put("goodsAmount", goodsAmount);
//        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
//        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("cartList", cartList);
//        result.put("cartTotal", cartTotal);
//        return JsonResult.ok(result);
//    }
//
//    /**
//     * 加入商品到购物车
//     * <p>
//     * 如果已经存在购物车货品，则增加数量；
//     * 否则添加新的购物车货品项。
//     * @param
//     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
//     * @return 加入购物车操作结果
//     */
//    @PostMapping("add")
//    @ApiOperation(value = "添加购物车",notes = "添加购物车")
//    public JsonResult add(@RequestBody LitemallCart cart)  {
//        Integer userId = getUserId();
//        if (userId==null){
//            return JsonResult.unAuthorized("未登陆");
//        }
//        if (cart == null) {
//            return JsonResult.error("cart不能为空");
//        }
//
//        Integer productId = cart.getProductId();
//        Integer number = cart.getNumber().intValue();
//        Integer goodsId = cart.getGoodsId();
//        if (!ObjectUtils.allNotNull(number, goodsId)) {
//            return JsonResult.error("number,goodsId不能为空");
//        }
//        if(number <= 0){
//            return JsonResult.error("参数错误");
//        }
//
//        //判断商品是否可以购买
//        LitemallGoods goods = goodsService.findById(goodsId);
//        if (goods == null || !goods.getIsOnSale()) {
//            return JsonResult.error("商品已下架");
//        }
//
//        //如果productId为空  那么就取第一个
//
//        if (!ObjectUtils.allNotNull(productId)){
//            //赋值第一个元素
//            final List<LitemallGoodsProduct> litemallGoodsProducts = productService.queryByGid(goodsId);
//            if (litemallGoodsProducts!=null &&litemallGoodsProducts.size()>0){
//                productId=litemallGoodsProducts.get(0).getId();
//            }else{
//                return JsonResult.error("参数错误");
//            }
//        }
//
//        LitemallGoodsProduct product = productService.findById(productId);
//        //判断购物车中是否存在此规格商品
//        LitemallCart existCart = cartService.queryExist(goodsId, productId, userId);
//        if (existCart == null) {
//            //取得规格的信息,判断规格库存
//            if (product == null || number > product.getNumber()) {
//                return JsonResult.error("库存不足");
//            }
//
//            cart.setId(null);
//            cart.setGoodsSn(goods.getGoodsSn());
//            cart.setGoodsName((goods.getName()));
//            cart.setPicUrl(goods.getPicUrl());
//            cart.setProductId(product.getId());
//            cart.setPrice(product.getPrice());
//            cart.setSpecifications(product.getSpecifications());
//            cart.setUserId(userId);
//            cart.setChecked(true);
//            cartService.add(cart);
//        } else {
//            //取得规格的信息,判断规格库存
//            int num = existCart.getNumber() + number;
//            if (num > product.getNumber()) {
//                return JsonResult.error("库存不足");
//            }
//            existCart.setNumber((short) num);
//            if (cartService.updateById(existCart) == 0) {
//                return JsonResult.error("更新失败");
//            }
//        }
//
//        return goodscount();
//    }
//
//    /**
//     * 立即购买
//     * <p>
//     * 和add方法的区别在于：
//     * 1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
//     * 2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
//     *
//     * @param
//     * @param cart   购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
//     * @return 立即购买操作结果
//     */
//    @PostMapping("fastadd")
//    @ApiOperation(value = "快速添加(目前未使用)",notes = "快速添加(目前未使用)")
//    public JsonResult fastadd(@RequestBody LitemallCart cart)  {
//        Integer userId = getUserId();
//        if (userId == null) {
//            return JsonResult.unAuthorized("未登陆");
//        }
//        if (cart == null) {
//            return JsonResult.error("参数错误");
//        }
//
//        Integer productId = cart.getProductId();
//        Integer number = cart.getNumber().intValue();
//        Integer goodsId = cart.getGoodsId();
//        if (!ObjectUtils.allNotNull(productId, number, goodsId)) {
//            return JsonResult.error("参数错误");
//        }
//        if(number <= 0){
//            return JsonResult.error("参数错误");
//        }
//
//        //判断商品是否可以购买
//        LitemallGoods goods = goodsService.findById(goodsId);
//        if (goods == null || !goods.getIsOnSale()) {
//            return JsonResult.error("商品已下架");
//        }
//
//        LitemallGoodsProduct product = productService.findById(productId);
//        //判断购物车中是否存在此规格商品
//        LitemallCart existCart = cartService.queryExist(goodsId, productId, userId);
//        if (existCart == null) {
//            //取得规格的信息,判断规格库存
//            if (product == null || number > product.getNumber()) {
//                return JsonResult.error("库存不足");
//            }
//
//            cart.setId(null);
//            cart.setGoodsSn(goods.getGoodsSn());
//            cart.setGoodsName((goods.getName()));
//            cart.setPicUrl(goods.getPicUrl());
//            cart.setPrice(product.getPrice());
//            cart.setSpecifications(product.getSpecifications());
//            cart.setUserId(userId);
//            cart.setChecked(true);
//            cartService.add(cart);
//        } else {
//            //取得规格的信息,判断规格库存
//            int num = number;
//            if (num > product.getNumber()) {
//                return JsonResult.error("库存不足");
//            }
//            existCart.setNumber((short) num);
//            if (cartService.updateById(existCart) == 0) {
//                return JsonResult.error("更新失败");
//            }
//        }
//
//        return JsonResult.ok(existCart != null ? existCart.getId() : cart.getId());
//    }
//
//
//
//
//
//    @PostMapping("buyNow")
//    @ApiOperation(value = "立即购买",notes = "立即购买")
//    public JsonResult buyNow(@RequestBody LitemallCart cart) {
//        if (cart == null) {
//            return JsonResult.error("参数错误");
//        }
//        //Integer userId = getUserId();
//        Integer productId = cart.getProductId();
//        Integer number = cart.getNumber().intValue();
//        Integer goodsId = cart.getGoodsId();
//        BigDecimal price = cart.getPrice();
//        if (!ObjectUtils.allNotNull(productId, number, goodsId,price)) {
//            return JsonResult.error("参数错误");
//        }
//        if(number <= 0){
//            return JsonResult.error("参数错误");
//        }
//
//        //判断商品是否可以购买
//        LitemallGoods goods = goodsService.findById(goodsId);
//        if (goods == null || !goods.getIsOnSale()) {
//            return JsonResult.error("商品已下架");
//        }
//
//        //检查交易有没有问题
//        String error = productService.preCheckProduct(productId, number, price);
//
//        if (StringUtils.isNotEmpty(error)){
//            return JsonResult.error(error);
//        }
//
//        /*LitemallOrder order=new LitemallOrder();
//        order.setUserId(userId);
//        order.setOrderSn(IdGeneratorUtils.getDefaultInstance().nextId());
//        //0 表示新创建
//        order.setOrderStatus((short) 0);
//        order.setGoodsPrice(price);
//
//
//        //调用线程保存至数据库
//        ThreadPoolUtil.setThreadName("order-async-task").execute(new SaveOrderThread(order,orderService));*/
//
//        return JsonResult.ok("购买成功");
//    }
//    /**
//     * 修改购物车商品货品数量
//     *
//     * @param
//     * @param cart   购物车商品信息， { id: xxx, goodsId: xxx, productId: xxx, number: xxx }
//     * @return 修改结果
//     */
//    @PostMapping("update")
//    @ApiOperation(value = "修改购物车数量",notes = "修改购物车数量")
//    public JsonResult update(@RequestBody LitemallCart cart)  {
//        Integer userId = getUserId();
//        if (userId == null) {
//            return JsonResult.unAuthorized("未登陆");
//        }
//        if (cart == null) {
//            return JsonResult.error("参数错误");
//        }
//        Integer productId = cart.getProductId();
//        Integer number = cart.getNumber().intValue();
//        Integer goodsId = cart.getGoodsId();
//        Integer id = cart.getId();
//        if (!ObjectUtils.allNotNull(id, productId, number, goodsId)) {
//            return JsonResult.error("参数错误");
//        }
//        if(number <= 0){
//            return JsonResult.error("参数错误");
//        }
//
//        //判断是否存在该订单
//        // 如果不存在，直接返回错误
//        LitemallCart existCart = cartService.findById(id);
//        if (existCart == null) {
//            return JsonResult.error("参数错误");
//        }
//
//        // 判断goodsId和productId是否与当前cart里的值一致
//        if (!existCart.getGoodsId().equals(goodsId)) {
//            return JsonResult.error("参数错误");
//        }
//        if (!existCart.getProductId().equals(productId)) {
//            return JsonResult.error("参数错误");
//        }
//
//        //判断商品是否可以购买
//        LitemallGoods goods = goodsService.findById(goodsId);
//        if (goods == null || !goods.getIsOnSale()) {
//            return JsonResult.error("商品已下架");
//
//        }
//
//        //取得规格的信息,判断规格库存
//        LitemallGoodsProduct product = productService.findById(productId);
//        if (product == null || product.getNumber() < number) {
//            return JsonResult.error("库存不足");
//        }
//
//        existCart.setNumber(number.shortValue());
//        if (cartService.updateById(existCart) == 0) {
//            return JsonResult.error("更新失败");
//        }
//        return goodscount();
//    }
//
//    /**
//     * 购物车商品货品勾选状态
//     * <p>
//     * 如果原来没有勾选，则设置勾选状态；如果商品已经勾选，则设置非勾选状态。
//     *
//     * @param userId 用户ID
//     * @param body   购物车商品信息， { productIds: xxx, isChecked: 1/0 }
//     * @return 购物车信息
//     */
///*    @PostMapping("checked")
//    @ApiOperation(value = "修改购物车数量",notes = "修改购物车数量")
//    public JsonResult checked(@LoginUser Integer userId, @RequestBody String body) {
//        if (userId == null) {
//            return ResponseUtil.unlogin();
//        }
//        if (body == null) {
//            return ResponseUtil.badArgument();
//        }
//
//        List<Integer> productIds = JacksonUtil.parseIntegerList(body, "productIds");
//        if (productIds == null) {
//            return ResponseUtil.badArgument();
//        }
//
//        Integer checkValue = JacksonUtil.parseInteger(body, "isChecked");
//        if (checkValue == null) {
//            return ResponseUtil.badArgument();
//        }
//        Boolean isChecked = (checkValue == 1);
//
//        cartService.updateCheck(userId, productIds, isChecked);
//        return index();
//    }*/
//
//    /**
//     * 购物车商品删除
//     *
//     */
//    @PostMapping("delete")
//    @ApiOperation(value = "购物车商品删除",notes = "购物车商品删除")
//    public JsonResult delete(@RequestBody String body) {
//        Integer userId = getUserId();
//        if (userId == null) {
//            return JsonResult.unAuthorized("未登陆");
//        }
//        if (body == null) {
//            return JsonResult.error("参数错误");
//        }
//
//        List<Integer> productIds = JacksonUtil.parseIntegerList(body, "productIds");
//
//        if (productIds == null || productIds.size() == 0) {
//            return JsonResult.error("参数错误");
//        }
//
//        cartService.delete(productIds, userId);
//        return index();
//    }
//
//    /**
//     * 购物车商品货品数量
//     * <p>
//     * 如果用户没有登录，则返回空数据
//     * @param
//     * @return 购物车商品货品数量
//     */
//    @GetMapping("goodscount")
//    @ApiOperation(value = "购物车商品数量",notes = "购物车商品数量")
//    public JsonResult goodscount() {
//        Integer userId = getUserId();
//        if (userId == null) {
//            return JsonResult.ok(0);
//        }
//
//        int goodsCount = 0;
//        List<LitemallCart> cartList = cartService.queryByUid(userId);
//        for (LitemallCart cart : cartList) {
//            goodsCount += cart.getNumber();
//        }
//
//        return JsonResult.ok(goodsCount);
//    }
//
//    /**
//     * 购物车下单
//     *
//     * @param
//     * @param cartId    购物车商品ID：
//     *                  如果购物车商品ID是空，则下单当前用户所有购物车商品；
//     *                  如果购物车商品ID非空，则只下单当前购物车商品。
//     * @param addressId 收货地址ID：
//     *                  如果收货地址ID是空，则查询当前用户的默认地址。
//     * @param couponId  优惠券ID：
//     *                  如果优惠券ID是空，则自动选择合适的优惠券。
//     * @return 购物车操作结果
//     */
//    @GetMapping("checkout")
//    @ApiOperation(value = "购物车下单",notes = "购物车下单")
//    public JsonResult checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
//        Integer userId = getUserId();
//        if (userId == null) {
//            return JsonResult.unAuthorized("未登陆");
//        }
//
//        // 收货地址
//        LitemallAddress checkedAddress = null;
//        if (addressId == null || addressId.equals(0)) {
//            checkedAddress = addressService.findDefault(userId);
//            // 如果仍然没有地址，则是没有收获地址
//            // 返回一个空的地址id=0，这样前端则会提醒添加地址
//            if (checkedAddress == null) {
//                checkedAddress = new LitemallAddress();
//                checkedAddress.setId(0);
//                addressId = 0;
//            } else {
//                addressId = checkedAddress.getId();
//            }
//
//        } else {
//            checkedAddress = addressService.query(userId, addressId);
//            // 如果null, 则报错
//            if (checkedAddress == null) {
//                return JsonResult.error("参数错误");
//            }
//        }
//
//        // 团购优惠
//        BigDecimal grouponPrice = new BigDecimal(0.00);
//        LitemallGrouponRules grouponRules = grouponRulesService.queryById(grouponRulesId);
//        if (grouponRules != null) {
//            grouponPrice = grouponRules.getDiscount();
//        }
//
//        // 商品价格
//        List<LitemallCart> checkedGoodsList = null;
//        if (cartId == null || cartId.equals(0)) {
//            checkedGoodsList = cartService.queryByUidAndChecked(userId);
//        } else {
//            LitemallCart cart = cartService.findById(cartId);
//            if (cart == null) {
//                return JsonResult.error("参数错误");
//            }
//            checkedGoodsList = new ArrayList<>(1);
//            checkedGoodsList.add(cart);
//        }
//        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
//        for (LitemallCart cart : checkedGoodsList) {
//            //  只有当团购规格商品ID符合才进行团购优惠
//            if (grouponRules != null && grouponRules.getGoodsId().equals(cart.getGoodsId())) {
//                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
//            } else {
//                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
//            }
//        }
//
//        // 计算优惠券可用情况
//        BigDecimal tmpCouponPrice = new BigDecimal(0.00);
//        Integer tmpCouponId = 0;
//        int tmpCouponLength = 0;
//        List<LitemallCouponUser> couponUserList = couponUserService.queryAll(userId);
//        for(LitemallCouponUser couponUser : couponUserList){
//            LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), checkedGoodsPrice);
//            if(coupon == null){
//                continue;
//            }
//
//            tmpCouponLength++;
//            if(tmpCouponPrice.compareTo(coupon.getDiscount()) == -1){
//                tmpCouponPrice = coupon.getDiscount();
//                tmpCouponId = coupon.getId();
//            }
//        }
//        // 获取优惠券减免金额，优惠券可用数量
//        int availableCouponLength = tmpCouponLength;
//        BigDecimal couponPrice = new BigDecimal(0);
//        // 这里存在三种情况
//        // 1. 用户不想使用优惠券，则不处理
//        // 2. 用户想自动使用优惠券，则选择合适优惠券
//        // 3. 用户已选择优惠券，则测试优惠券是否合适
//        if (couponId == null || couponId.equals(-1)){
//            couponId = -1;
//        }
//        else if (couponId.equals(0)) {
//            couponPrice = tmpCouponPrice;
//            couponId = tmpCouponId;
//        }
//        else {
//            LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponId, checkedGoodsPrice);
//            // 用户选择的优惠券有问题，则选择合适优惠券，否则使用用户选择的优惠券
//            if(coupon == null){
//                couponPrice = tmpCouponPrice;
//                couponId = tmpCouponId;
//            }
//            else {
//                couponPrice = coupon.getDiscount();
//            }
//        }
//
//        // 根据订单商品总价计算运费，满88则免运费，否则8元；
//        BigDecimal freightPrice = new BigDecimal(0.00);
//        if (checkedGoodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
//            freightPrice = SystemConfig.getFreight();
//        }
//
//        // 可以使用的其他钱，例如用户积分
//        BigDecimal integralPrice = new BigDecimal(0.00);
//
//        // 订单费用
//        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice);
//        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("addressId", addressId);
//        data.put("couponId", couponId);
//        data.put("cartId", cartId);
//        data.put("grouponRulesId", grouponRulesId);
//        data.put("grouponPrice", grouponPrice);
//        data.put("checkedAddress", checkedAddress);
//        data.put("availableCouponLength", availableCouponLength);
//        data.put("goodsTotalPrice", checkedGoodsPrice);
//        data.put("freightPrice", freightPrice);
//        data.put("couponPrice", couponPrice);
//        data.put("orderTotalPrice", orderTotalPrice);
//        data.put("actualPrice", actualPrice);
//        data.put("checkedGoodsList", checkedGoodsList);
//        return JsonResult.ok(data);
//    }
//}