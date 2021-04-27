package com.csxy.project.model.sys;

import java.util.HashMap;
import java.util.Map;

public class RedisInfoDetail {
	private static Map<String, String> map = new HashMap<String, String>();
	
	private String key;
	private String value;
	private String desctiption;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
		this.desctiption = map.get(this.key);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesctiption() {
		return desctiption;
	}
	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}
	@Override
	public String toString() {
		return "RedisInfoDetail [key=" + key + ", value=" + value
				+ ", desctiption=" + desctiption + "]";
	}
}
