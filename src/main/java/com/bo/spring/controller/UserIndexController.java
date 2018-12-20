package com.bo.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bo.spring.model.entity.GameUsers;
import com.bo.spring.model.mapper.GameUsersMapper;
import com.bo.spring.utils.RequestUtils;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserIndexController extends AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(UserIndexController.class);

	@Resource
	private GameUsersMapper usersMapper;
	
	@ApiOperation("用户信息")
	@GetMapping("v1/user")
	@RequiresAuthentication
	public Map<String, Object> user(ModelMap model){
		log.info("getText Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", "larry");
		resultMap.put("device", RequestUtils.getDevice());
		
		GameUsers users = usersMapper.selectByLoginname("test555");
		resultMap.put(data, users);
		log.info("token:{}",  model.get("token"));
		return resultMap;
	}
	
	@GetMapping("v1/index")
	@RequiresAuthentication
	public Map<String, Object> index(){
		log.info("getText Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", "larry");
		return resultMap;
	}
	
	@GetMapping("v2/login")
	public Map<String, Object> login(){
		log.info("getText Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("test555","a123456","member");
		try {
			subject.login(token);
		} catch (Exception e) {
			 log.error("错误", e);
			return resultMap;
		}
		resultMap.put("name", "larry");
		return resultMap;
	}
	
	@GetMapping("v2/game")
	public Map<String, Object> game(){
		log.info("getText Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", "larry");
		return resultMap;
	}
	
	
	@GetMapping("v3/t1")
	public Map<String, Object> t1(){
		log.info("t1 Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", "larry");
		return resultMap;
	}
	
	@GetMapping("v3/t2")
	public Map<String, Object> t2(){
		log.info("t2 Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", "larry");
		return resultMap;
	}
	
	@GetMapping(value = "/unauth")
	@ResponseBody
	public Object unauth() {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("code", "1000000");
	    map.put("msg", "未登录");
	    return map;
	}
	 
}
