package com.unioncast.db.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Configuration
//@PropertySource(value = "classpath:/redis.properties")
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    // @Value("${spring.redis.host}")
    // private String host;
    // @Value("${spring.redis.port}")
    // private int port;
    // @Value("${spring.redis.timeout}")
    // private int timeout;
    private static final Logger LOG = LogManager.getLogger(RedisConfig.class);

    @Value("${spring.redis.host1}")
    private String host1;

    @Value("${spring.redis.host2}")
    private String host2;

    @Value("${spring.redis.host3}")
    private String host3;

    @Value("${spring.redis.host4}")
    private String host4;

    @Value("${spring.redis.host5}")
    private String host5;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.portOther}")
    private int portOther;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

	/*
     * @Value("${spring.redis.password}") private String password;
	 */

    // Jedis连接池
/*	@Bean
	public JedisPool redisPoolFactory() {
		LOG.info("JedisPool注入成功！！");
		LOG.info("redis地址：" + host1 + ":" + port);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

		// JedisPool jedisPool = new JedisPool(jedisPoolConfig, host1, port,
		// timeout, password);
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host1, port, timeout);

		return jedisPool;
	}*/

    // JedisCluster

    @Bean
    public JedisCluster JedisClusterFactory() {
        LOG.info("JedisCluster创建！！");
        LOG.info("redis地址：" + host1 + ":" + port);
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //jedisClusterNodes.add(new HostAndPort(host1, port));
        //jedisClusterNodes.add(new HostAndPort(host2, port));
        jedisClusterNodes.add(new HostAndPort(host3, port));
        //jedisClusterNodes.add(new HostAndPort(host3, portOther));
        jedisClusterNodes.add(new HostAndPort(host4, port));
        jedisClusterNodes.add(new HostAndPort(host5, port));
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, jedisPoolConfig);
        return jedisCluster;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host1);
        factory.setPort(port);
        factory.setTimeout(timeout); // 设置连接超时时间
        return factory;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
        return cacheManager;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template); // 设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
