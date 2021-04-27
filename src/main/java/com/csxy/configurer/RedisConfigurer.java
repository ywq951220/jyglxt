package com.csxy.configurer;

import com.csxy.core.util.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableCaching
public class RedisConfigurer extends CachingConfigurerSupport {

    //获取Redis所在IP地址
    @Value("${spring.redis.host}")
    private String host;

    //获取Redis端口
    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    //获取Redis最大等待时间
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    //获取Redis连接密码
    @Value("${spring.redis.password}")
    private String password;

    /**
     * Redis初始化，进行Redis的连接
     * @param
     * @return
     */
    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);
        Logger.getLogger(getClass()).info("JedisPool注入成功！！");
        Logger.getLogger(getClass()).info("redis地址：" + host + ":" + port);
        return jedisPool;
    }
}
