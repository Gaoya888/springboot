package com.bo.spring.controller.init;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringInit {

	private static final Logger log = LoggerFactory.getLogger(SpringInit.class);
	
	/**
	 * 项目启动时，初始化
	* @author larry
	* @date 2018年12月10日 下午9:00:48
	*
	 */
	@PostConstruct
	public void initWebSiteConfig() {
		log.info("*****初始化完成*****");
	}
	
	@Scheduled(cron = "0 */1 * * * ?") // 每1分钟,进行更新一次
	//@Scheduled(fixedRate = 6000)
	public void initScheduledCMD() {
		log.info("*****更新6*****");
	}
	
	//@Scheduled(fixedRate = 10000)
	public void initScheduledSB() {
		log.info("*****更新10*****");
	}
}
