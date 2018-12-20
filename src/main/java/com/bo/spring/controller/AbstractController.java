package com.bo.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

public class AbstractController {

	protected static final String state = "state";
	protected static final String data = "data";

	protected static final Boolean successState = true;
	protected static final Boolean errorState = false;

	protected static final String defaultSuccessData = "success";
	protected static final String defaultErrorData = "操作失败,请稍后再试!";
	
	protected static final String validateError = "不能为空!";

	// 验证码字段
	protected static final AtomicBoolean openStaffKaptcha = new AtomicBoolean(true);// 后台管理默认开启验证码
	protected static final AtomicBoolean openMemberKaptcha = new AtomicBoolean(true);// 后台管理默认开启验证码

	/**
	 * 不为空检查 返回第一个为null的value的key,否则返回null
	 */
	protected String validateParamsByMap(Map<String, Object> paramsMap) {
		for (Entry<String, Object> entry : paramsMap.entrySet()) {
			if (entry.getValue() instanceof List) {
				@SuppressWarnings("rawtypes")
				List list = (List) entry.getValue();
				if (list == null || list.size() == 0) {
					return entry.getKey();
				}
			}
			if (entry.getValue() == null) {
				return entry.getKey();
			}
		}
		return null;
	}

}