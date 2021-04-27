package com.csxy.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.Slowlog;
//import redis.clients.util.Slowlog;

import java.util.List;

@Component
public class RedisUtil {

    @Autowired
    JedisPool jedisPool;

    public String getRedisInfo() {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Client client = jedis.getClient();
            client.info();
            return client.getBulkReply();
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    public List<Slowlog> getLogs(long entries) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.slowlogGet(entries);
        } finally {
            assert jedis != null;
            jedis.close();
        }
    }

    public Long getLogsLen() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long logLen = jedis.slowlogLen();
            return logLen;
        } finally {
            jedis.close();
        }
    }

    public String logEmpty() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.slowlogReset();
        } finally {
            jedis.close();
        }
    }

    public Long dbSize() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Client client = jedis.getClient();
            client.dbSize();
            return client.getIntegerReply();
        } finally {
            jedis.close();
        }
    }
}
