package com.provider.umc.api.service.hystrix;

import com.ev.cloud.db.domain.LitemallAddress;
import com.ev.cloud.db.domain.LitemallFeedback;
import com.ev.cloud.db.dto.WxLoginInfo;
import com.ev.common.base.dto.JsonResult;
import com.provider.umc.api.service.WxUserClient;

/**
 * @author
 * @create 2019-08-02 10:03 AM
 **/
public class WxUserHystrix implements WxUserClient{
    @Override
    public JsonResult list() {
        return null;
    }

    @Override
    public JsonResult detail(Integer id) {
        return null;
    }

    @Override
    public JsonResult save(LitemallAddress address) {
        return null;
    }

    @Override
    public JsonResult delete(LitemallAddress address) {
        return null;
    }

    @Override
    public JsonResult loginByWeixin(WxLoginInfo wxLoginInfo) {
        return null;
    }

    @Override
    public JsonResult sessionEnable() {
        return null;
    }

    @Override
    public JsonResult logout() {
        return null;
    }

    @Override
    public JsonResult list(Integer page, Integer limit, String sort, String order) {
        return null;
    }

    @Override
    public JsonResult mylist(Short status, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    @Override
    public JsonResult selectlist(Integer cartId, Integer grouponRulesId) {
        return null;
    }

    @Override
    public JsonResult receive(String body) {
        return null;
    }

    @Override
    public JsonResult exchange(String body) {
        return null;
    }

    @Override
    public JsonResult submit(LitemallFeedback feedback) {
        return null;
    }
}
