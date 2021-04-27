package com.csxy.project.web.sys;

import com.alibaba.fastjson.JSON;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.project.model.sys.Operate;
import com.csxy.project.model.sys.RedisInfoDetail;
import com.csxy.project.service.sys.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	RedisService redisService;
	
	@RequestMapping("/redisMonitor")
	public Result redisMonitor() {
		Map<String, Object> result = new HashMap<>();
		List<RedisInfoDetail> ridList = redisService.getRedisInfo();
		List<Operate> logList = redisService.getLogs(1000);
		long logLen = redisService.getLogLen();
		result.put("infoList", ridList);
		result.put("logList", logList);
		result.put("logLen", logLen);
		return ResultGenerator.genSuccessResult(result);
	}

	@RequestMapping("/logEmpty")
	public Result logEmpty(){
		redisService.logEmpty();
		return ResultGenerator.genSuccessResult();
	}
	
	@RequestMapping("/getKeysSize")
	public Result getKeysSize(){
		return ResultGenerator.genSuccessResult(JSON.toJSONString(redisService.getKeysSize()));
	}
	

	@RequestMapping("/getMemeryInfo")
	public Result getMemeryInfo(){
		return ResultGenerator.genSuccessResult(JSON.toJSONString(redisService.getMemeryInfo()));
	}
}
