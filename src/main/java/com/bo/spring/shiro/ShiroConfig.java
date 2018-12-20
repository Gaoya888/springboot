package com.bo.spring.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		 // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        // 拦截器 开放所有接口
		Map<String, String> map = new LinkedHashMap<String, String>();
		//放行Swagger2页面
		map.put("/swagger-ui.html","anon");
		map.put("/swagger/**","anon");
		map.put("/webjars/**", "anon");
		map.put("/swagger-resources/**","anon");
		
		map.put("/v1/*", "authc");
		map.put("/v2/*", "anon");
		map.put("/v3/*", "anon");
		map.put("/**", "authc");
		
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		shiroFilterFactoryBean.setLoginUrl("/unauth");
		
        return shiroFilterFactoryBean;
	}
	
	
	
	/**
	 * 注入 securityManager
     */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManger = new DefaultWebSecurityManager();
		//设置 realm
		securityManger.setRealm(memberRealm());
		
		return securityManger;
	}
	
	
	/**
	  * 自定义身份认证 realm;
               * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
               * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
	@Bean
	public MemberRealm memberRealm() {
		return new MemberRealm();
	}
}
