package com.csxy.core.common;

import com.csxy.project.dao.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class BaseController {

	@Autowired
	private RedisMapper redisMapper;

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	protected String getToken() {
		return this.getRequest().getHeader("CSXY-Token");
	}

	protected String getUserId() {
		return redisMapper.get(this.getToken());
	}
}
