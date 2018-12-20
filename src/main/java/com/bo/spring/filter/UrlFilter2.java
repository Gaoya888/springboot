package com.bo.spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
@WebFilter(filterName = "UrlFilter", urlPatterns = "/v2/*")
public class UrlFilter2 implements Filter {

	private static final Logger log = LoggerFactory.getLogger(UrlFilter2.class);
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("UrlFilter2");
		chain.doFilter(request, response);//执行请求
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
