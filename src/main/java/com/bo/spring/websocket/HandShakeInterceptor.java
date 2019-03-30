package com.bo.spring.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor  {

	protected static final String SESSIONKEY = "SHIROID";

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		log.info("Handle before webSocket connected. ");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String token = servletRequest.getServletRequest().getParameter("name");
			log.info("Validation passed. WebSocket connecting.... ");
			if (!StringUtils.isEmpty(token)) {
				attributes.put(SESSIONKEY, token);
				return true;
			}
		}
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
	}

}