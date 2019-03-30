package com.bo.spring.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebsocketMsg {

	private String method;

	private Object content;

}