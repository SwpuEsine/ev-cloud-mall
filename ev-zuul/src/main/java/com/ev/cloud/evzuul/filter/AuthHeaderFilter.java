/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthHeaderFilter.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ev.cloud.evzuul.filter;

import com.ev.cloud.evzuul.config.AuthConfig;
import com.ev.common.base.constant.GlobalConstant;
import com.ev.common.base.enums.ErrorCodeEnum;
import com.ev.common.base.exception.BusinessException;
import com.ev.common.core.utils.RequestUtil;
import com.ev.common.utils.PublicUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The class Auth header filter.
 *
 * @author paascloud.net @gmail.com
 */

/**
 * 对后面当微服务进行 授权处理
 */
@Slf4j
@Component
public class AuthHeaderFilter extends ZuulFilter {

	private static final String BEARER_TOKEN_TYPE = "bearer ";
	private static final String OPTIONS = "OPTIONS";



	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.tokenHead}")
	private String tokenHead;


	@Autowired
	AuthConfig authConfig;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	/**
	 * Filter type string.
	 *
	 * @return the string
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 越小级别越高
	 *
	 * @return the int
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * Should filter boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean shouldFilter() {

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest  request = requestContext.getRequest();

		//String authHeader = request.getHeader(this.tokenHeader);

		//如果请求路径为微信通知后台支付结果则不需要token（之后会在具体的controller中，对双方签名进行验证防钓鱼）
		String url = request.getRequestURI().substring(request.getContextPath().length());
		log.info("请求地址:"+url);
		List<String> annoymosUrls=authConfig.getAnnoymosUrls();

		AntPathMatcher antPathMatcher = new AntPathMatcher(System.getProperty("file.separator"));

		if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		for (String path:annoymosUrls) {
			if (antPathMatcher.match(path,request.getRequestURI())){
				//不鉴定权利
				return false;
			}
		}
		//是否启用
		return true;
	}

	/**
	 * Run object.
	 *
	 * @return the object
	 */
	@Override
	public Object run() {
		log.info("AuthHeaderFilter - 开始鉴权...");
		RequestContext requestContext = RequestContext.getCurrentContext();
		try {
			doSomething(requestContext);
		} catch (Exception e) {
			log.error("AuthHeaderFilter - [FAIL] EXCEPTION={}", e.getMessage(), e);
			throw new BusinessException(ErrorCodeEnum.UAC10011041);
		}
		return null;
	}

	private void doSomething(RequestContext requestContext) throws ZuulException {
		//判断鉴权 授权头
		HttpServletRequest request = requestContext.getRequest();

		String authHeader = request.getHeader(this.tokenHeader);

		if (PublicUtil.isEmpty(authHeader)) {
			throw new ZuulException("刷新页面重试", 403, "check token fail");
		}

		if (authHeader.startsWith(BEARER_TOKEN_TYPE)) {

			final String thirdSessionId = authHeader.substring(tokenHead.length());
			String token = stringRedisTemplate.opsForValue().get(thirdSessionId);

			if (StringUtils.isEmpty(token)) {
				throw new BusinessException("用户身份已过期");
			}
			//传递token
			requestContext.addZuulRequestHeader(GlobalConstant.SESSION_KEY, thirdSessionId);
			log.info("传递token authHeader={} ", token);
			// 传递给后续微服务 这里先不关
			//requestContext.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, token);
		}else{
			throw new ZuulException("token异常", 403, "token header error");
		}
	}

}
