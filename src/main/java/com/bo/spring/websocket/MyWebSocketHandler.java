package com.bo.spring.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MyWebSocketHandler implements WebSocketHandler {

	private static final Map<String, WebSocketSession> STAFFSESSIONS = new ConcurrentHashMap<>();
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
	//@Resource
	//private ShiroSessionDAO sessionDAO;

	/**
	 * websocket关闭后
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		STAFFSESSIONS.remove(session.getAttributes().get(HandShakeInterceptor.SESSIONKEY).toString());
	}

	/**
	 * websocket连接后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String name = session.getAttributes().get(HandShakeInterceptor.SESSIONKEY).toString();
		String requestID = session.getAttributes().get(HandShakeInterceptor.REQUESTID).toString();
		//Session shiroSession = sessionDAO.doReadSession(sessionId);
		//log.info("websocket连接------------------------------>shiroSession:{}", shiroSession.getId());
		//log.info("websocket连接------------------------------>:员工");
		log.info("websocket连接------------------------------>:员工:"+name);
		STAFFSESSIONS.put(requestID, session);
	}

	/**
	 * 消息处理,在客户端通过Websocket API发送的消息会经过这里,然后进行相应的处理
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("websocket收到的消息--------------------->{}", JSON.toJSON(message.getPayload()));
		if (message.getPayloadLength() == 0) {
			return;
		}
	}

	/**
	 * 消息传输错误处理
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		// 移除Socket会话
		STAFFSESSIONS.remove(session.getAttributes().get(HandShakeInterceptor.SESSIONKEY).toString());
		log.error("websocket消息传输错误,关闭session:{}", session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 */
	public void sendMessageToUsers(WebsocketMsg msg) {
		log.info("发送消息-------------------------------->{}", JSON.toJSON(msg));
		
		Runnable t = new Runnable() {
            @Override
            public void run() {
            	for (WebSocketSession session : STAFFSESSIONS.values()) {
            		synchronized (session) {
		    			if (session.isOpen()) {
		    				try {
		    					session.sendMessage(new TextMessage(JSONObject.toJSONBytes(msg)));
		    				} catch (IOException e) {
		    					log.error("websocket发送给所有会员信息错误:{}", session.getId());
		    				}
		    			}
            		}
            	}
            }
        };
        
        fixedThreadPool.submit(t);
	}

	/**
	 * 给某个用户发送消息
	 */
	@Async
	public void sendMessageToUser(String sessionId, WebsocketMsg msg) {
		WebSocketSession session = STAFFSESSIONS.get(sessionId);
		if (session != null && session.isOpen()) {
			try {
				session.sendMessage(new TextMessage(JSONObject.toJSONBytes(msg)));
			} catch (IOException e) {
				e.printStackTrace();
				log.error("websocket发送给某人信息错误:{}", sessionId);
			}
		}
	}

}