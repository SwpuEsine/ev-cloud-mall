package com.provider.umc.api.service;


import com.ev.cloud.db.domain.LitemallAddress;
import com.ev.cloud.db.domain.LitemallFeedback;
import com.ev.cloud.db.dto.WxLoginInfo;
import com.ev.common.base.dto.JsonResult;
import com.provider.umc.api.service.hystrix.WxUserHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//404也会发生熔断情况

@FeignClient(path = "wx/user",value = "provider-umc", fallback = WxUserHystrix.class)
public interface WxUserClient {

    @GetMapping(value = "/inner/wx/address/list")
    public JsonResult list();

    @GetMapping("detail")
    public JsonResult detail(Integer id);

    @PostMapping("save")
    public JsonResult save(@RequestBody LitemallAddress address);

    @PostMapping("delete")
    public JsonResult delete(@RequestBody LitemallAddress address) ;


    @PostMapping("login_by_weixin")
    public JsonResult loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo);

    @PostMapping("sessionEnable")
    public JsonResult sessionEnable();

    @PostMapping("logout")
    public JsonResult logout();


    @GetMapping("list")
    public JsonResult list(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @RequestParam(defaultValue = "add_time") String sort,
                           @RequestParam(defaultValue = "desc") String order);

    @GetMapping("mylist")
    public JsonResult mylist(
            Short status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "add_time") String sort,
            @RequestParam(defaultValue = "desc") String order);

    @GetMapping("selectlist")
    public JsonResult selectlist( Integer cartId, Integer grouponRulesId);

    @PostMapping("receive")
    public JsonResult receive(@RequestBody String body);


    @PostMapping("exchange")
    public JsonResult exchange(@RequestBody String body);

    @PostMapping("submit")
    public JsonResult submit(@RequestBody LitemallFeedback feedback);
}
