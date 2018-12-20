package com.bo.spring.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bo.spring.controller.AbstractController;

/**
 * 
* @ClassName: GlobalHandlerExceptionAdvice 
* @author larry
* @date 2018年12月14日 下午3:42:10 
*    注解@ControllerAdvice的类可以拥有 @ExceptionHandler, @InitBinder或 @ModelAttribute注解的方法，
*    并且这些方法会被应用到控制器类层次的所有 @RequestMapping方法上
 */
@RestControllerAdvice
public class GlobalHandlerExceptionAdvice extends AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalHandlerExceptionAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public Map<String, Object> handleException(Exception e) {
		log.error("全局捕捉异常,异常信息:{}", e);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(state, errorState);
		resultMap.put(data, defaultErrorData);
		return resultMap;
	}
	
	@ModelAttribute
	public void addAttribute(Model model) {
		model.addAttribute("token", "1234567890");
	}
}
