package com.bo.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bo.spring.filter.UrlFilter3;
import com.bo.spring.handler.GlobalHandlerInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport{

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globalHandlerInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns(
				"/index",
                "/login",
                "/doLogin",
                "/logout",
                "/register",
                "/doRegister",
                "/**/*.js",
                "/**/*.css",
                "/**/*.css.map",
                "/**/*.jpeg",
                "/**/*.ico",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.woff",
                "/**/*.woff2" );
		super.addInterceptors(registry);
	}
	
	
	
	
	/**
     * View - Controller 映射配置
     */
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName("/index");
		super.addViewControllers(registry);
	}




	/**
           * 重写 addCorsMappings方法:
    addMapping：配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
    allowedMethods：允许所有的请求方法访问该跨域资源服务器，如：POST、GET、PUT、DELETE等。
    allowedOrigins：允许所有的请求域名访问我们的跨域资源，可以固定单条或者多条内容，如："http://www.baidu.com"，只有百度可以访问我们的跨域资源。
    allowedHeaders：允许所有的请求header访问，可以自定义设置任意请求头信息，如："X-TOKEN"
     */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("*")
        .allowedHeaders("*");
	}


	


	/**
	 * fastJson解析springmvc返回的json字符串
	 */
	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastConverter.setFastJsonConfig(fastJsonConfig);
		
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		fastConverter.setSupportedMediaTypes(supportedMediaTypes);
		converters.add(fastConverter);
	}

	 /**
	  * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     * 
     * @param registry
     */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
        		.addResourceLocations("classpath:/META-INF/resources/");
               
        registry.addResourceHandler("/webjars/**")
        		.addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
	}

	@Bean
	public HandlerInterceptor globalHandlerInterceptor() {
		return new GlobalHandlerInterceptor();
	}
	
	 /**
	  * 代码方式注册Filter
     * @return
     */
	@Bean
	public FilterRegistrationBean  urlFilter3() {
		FilterRegistrationBean  filterBean = new FilterRegistrationBean ();
		filterBean.setFilter(new UrlFilter3());
		filterBean.setName("UrlFilter3");
        filterBean.addUrlPatterns("/v3/*");
        filterBean.setOrder(1);
		return filterBean;
	}
	
}
