package com.csxy.project.service.sys;

import com.alibaba.fastjson.JSON;
import com.csxy.core.util.RedisUtil;
import com.csxy.project.dao.RedisMapper;
import com.csxy.project.model.sys.Operate;
import com.csxy.project.model.sys.RedisInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.util.Slowlog;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RedisService {

	@Autowired
    RedisUtil redisUtil;

	@Autowired
    RedisMapper redisMapper;

	/**
	 * 获取Redis的基本信息
	 * @param
	 * @return
	 */
	public List<RedisInfoDetail> getRedisInfo() {
		String info = redisUtil.getRedisInfo();
		List<RedisInfoDetail> ridList = new ArrayList<RedisInfoDetail>();
		String[] strs = info.split("\n");
		RedisInfoDetail rif = null;
		if (strs != null && strs.length > 0) {
			for (int i = 0; i < strs.length; i++) {
				rif = new RedisInfoDetail();
				String s = strs[i];
				String[] str = s.split(":");
				if (str != null && str.length > 1) {
					String key = str[0];
					String value = str[1];
					rif.setKey(key);
					rif.setValue(value);
					ridList.add(rif);
				}
			}
		}
		return ridList;
	}

	/**
	 * 获取Redis的日志信息
	 * @param entries
	 * @return
	 */
	public List<Operate> getLogs(long entries) {
        List<Slowlog> list = redisUtil.getLogs(entries);
		List<Operate> opList = null;
		Operate op  = null;
		boolean flag = false;
		if (list != null && list.size() > 0) {
			opList = new LinkedList<Operate>();
			for (Slowlog sl : list) {
				String args = JSON.toJSONString(sl.getArgs());
				if (args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
					continue;
				}
				op = new Operate();
				flag = true;
				op.setId(sl.getId());
				op.setExecuteTime(getDateStr(sl.getTimeStamp() * 1000));
				op.setUsedTime(sl.getExecutionTime()/1000.0 + "ms");
				op.setArgs(args);
				opList.add(op);
			}
		}
		if (flag) {
			return opList;
		} else {
			return null;
		}
	}
	public Long getLogLen() {
		return redisUtil.getLogsLen();
	}

	public String logEmpty() {
		return redisUtil.logEmpty();
	}

	public Map<String,Object> getKeysSize() {
		long dbSize = redisUtil.dbSize();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("create_time", System.currentTimeMillis());
		map.put("dbSize", dbSize);
		return map;
	}

	public Map<String,Object> getMemeryInfo() {
		String[] strs = redisUtil.getRedisInfo().split("\n");
		Map<String, Object> map = null;
		for (int i = 0; i < strs.length; i++) {
			String s = strs[i];
			String[] detail = s.split(":");
			if (detail[0].equals("used_memory")) {
				map = new HashMap<String, Object>();
				map.put("used_memory",detail[1].substring(0, detail[1].length() - 1));
				map.put("create_time", System.currentTimeMillis());
				break;
			}
		}
		return map;
	}

	/**
	 * 使用Redis进行用户信息的缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key,String value){
		return redisMapper.set(key,value);
	}

	/**
	 * 根据设置的token信息获取Redis缓存中的登录用户信息
	 * @param key
	 * @return
	 */
	public String get(String key){return redisMapper.get(key);}

	private String getDateStr(long timeStmp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date(timeStmp));
	}
}
