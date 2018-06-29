package com.boot.configs;

import com.boot.redis.JedisProperties;
import com.boot.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//@Configuration
//@EnableConfigurationProperties(JedisProperties.class)
//@ConditionalOnClass(RedisClient.class)
@Slf4j
/**
 * 配置Redis 连接池
 */
public class JedisConfig {

//    @Autowired
    private JedisProperties prop;


//    @Bean(name = "jedisPool")
    public JedisPool jedisPool() {
//        log.info("redis configProps:host:{} 、port:{}、prop:{}、password:{}",prop.getHost(), prop.getPort(), prop.getTimeOut(), prop.getPassword());
        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(prop.getMaxTotal());
//        config.setMaxIdle(prop.getMaxIdle());
//        config.setMaxWaitMillis(prop.getMaxWaitMillis());
        return new JedisPool(config, prop.getHost(), prop.getPort(), prop.getTimeOut(), prop.getPassword());
    }

//    @Bean
//    @ConditionalOnMissingBean(RedisClient.class)
    public RedisClient redisClient(@Qualifier("jedisPool") JedisPool pool) {
        log.info("初始化……Redis Client==Host={},Port={}", prop.getHost(), prop.getPort());
        RedisClient redisClient = new RedisClient();
        redisClient.setJedisPool(pool);
        return redisClient;
    }


}
