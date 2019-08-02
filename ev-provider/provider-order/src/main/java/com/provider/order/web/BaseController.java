package com.provider.order.web;


import com.ev.common.base.constant.GlobalConstant;
import com.ev.common.core.utils.RequestUtil;
import com.ev.common.core.utils.UserTokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @create 2019-05-12 下午6:51
 **/
public class BaseController {


    protected  Integer getUserId(){
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=RequestUtil.getAuthHeader(request);
        String currentUserWechatOpenId =token.substring(token.indexOf("#") + 1);
        if (StringUtils.isEmpty(currentUserWechatOpenId)){
            return null;
        }
        Integer userId = UserTokenManager.getUserId(currentUserWechatOpenId);
        return userId;
    }

    protected  String getUserSessionKey(){
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String thirdSessionId=request.getHeader(GlobalConstant.SESSION_KEY);
        return thirdSessionId;
    }

}
