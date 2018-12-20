package com.bo.spring.model.mapper;

import com.bo.spring.model.entity.GameUsers;

public interface GameUsersMapper {
	GameUsers selectByLoginname(String loginname);
}