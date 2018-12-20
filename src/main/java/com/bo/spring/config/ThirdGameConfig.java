package com.bo.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:config.properties")
public class ThirdGameConfig {
	
	@Value("${THRIDPARTY.HEADER}")
	private String thridpartyHeader;
	
	@Value("${PT.LOGIN.REDIRECT_URL}")
	private String ptloginUrl;

}