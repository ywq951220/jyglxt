package com.csxy.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisMapper {
    @Autowired
    JedisPool jedisPool;

    /**
     * 将登录用户的用户信息存储Redis缓存中
     * @param key
     * @param value
     * @return
     */
    public String set(String key,String value){
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } finally {
            assert jedis != null;
            jedis.close();
        }
        return result;
    }

    /**
     * 获取Redis中缓存的用户信息
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } finally {
            assert jedis != null;
            jedis.close();
        }
        return result;
    }

    public boolean exists(String key){
        Jedis jedis = null;
        boolean result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.exists(key);
        } finally {
            assert jedis != null;
            jedis.close();
        }
        return result;
    }

    /**
     * 清楚Redis中缓存的用户信息
     * @param key
     * @return
     */
    public boolean del(String key){
        Jedis jedis = null;
        long count = 0;
        try {
            jedis = jedisPool.getResource();
            count = jedis.del(key);
        } finally {
            assert jedis != null;
            jedis.close();
        }
        return count>0;
    }
}
