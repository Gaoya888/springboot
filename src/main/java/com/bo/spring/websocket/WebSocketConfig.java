package com.bo.spring.websocket;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Resource
	private MyWebSocketHandler webSocketHandler;

	@Resource
	private HandShakeInterceptor handshakeInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "ws").setAllowedOrigins("*").addInterceptors(handshakeInterceptor);
		registry.addHandler(webSocketHandler, "ws/sockjs").setAllowedOrigins("*").addInterceptors(handshakeInterceptor)
				.withSockJS();
	}
}