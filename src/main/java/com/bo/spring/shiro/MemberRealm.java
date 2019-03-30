package com.bo.spring.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bo.spring.service.UserInfoService;

public class MemberRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(MemberRealm.class);
	
	@Resource
	UserInfoService userInfoService;
	
	public static final String userType = "member";
	
	/**
	  *    获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken usernamePasswordToken) throws AuthenticationException {
		log.info("*****调用会员认证方法*****");
		
		UsernamePasswordToken token = (UsernamePasswordToken) usernamePasswordToken;
		String username = token.getUsername();
		String password = new String(token.getPassword());
		boolean f = userInfoService.login(username, password);
		if(!f) {
			throw new AccountException("密码不正确");
		}
		return new SimpleAuthenticationInfo(username, password, getName());
	}
	
	
	/**
              * 获取授权信息
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

}
