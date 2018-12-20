package com.bo.spring.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bo.spring.model.entity.GameUsers;
import com.bo.spring.model.mapper.GameUsersMapper;

@Service
public class UserInfoService {
	
	@Resource
	private GameUsersMapper usersMapper;
	
	public boolean login(String username, String password) {
		
		GameUsers  user = usersMapper.selectByLoginname(username);
		if(user!=null && password.equals(user.getPassword())) {
			return true;
		}
		
		return false;
	}
}
