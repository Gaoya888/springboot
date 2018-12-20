package com.bo.spring.handler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.bo.spring.utils.RequestUtils;

/**
 * 
* @ClassName: GlobalHandlerInterceptor 
* @Description: 拦截器
* @author larry
* @date 2018年12月14日 下午3:34:40 
*
 */
public class GlobalHandlerInterceptor implements HandlerInterceptor {

	
	private static final Logger log = LoggerFactory.getLogger(GlobalHandlerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 打印请求日志
		Map<String, String> parameterMap = new HashMap<>();
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String param = e.nextElement().toString();
			parameterMap.put(param, request.getParameter(param));
		}
		log.info("拦截器 请求地址:{},请求IP:{},请求参数:{}", request.getRequestURL(), RequestUtils.getIp(), JSON.toJSON(parameterMap));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle Completion");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("after Completion");
		
	}

}
