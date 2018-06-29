package com.boot.security.service;

import com.boot.entity.CacheKeyConstants;
import com.boot.util.JsonMapper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

/**
 * 描述：
 *
 * @author: zhangzy
 * @create: 2018-06-29
 */
@Service
@Slf4j
public class RedisServer {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix) {
        saveCache(toSavedValue, timeoutSeconds, prefix, null);
    }


    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys) {
        RedisConnection connection = redisConnectionFactory.getConnection();
        if (toSavedValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {

//            shardedJedis = redisPool.instance();
//            shardedJedis.setex(cacheKey, timeoutSeconds, toSavedValue);

            connection.setEx(cacheKey.getBytes(), timeoutSeconds, toSavedValue.getBytes());
        } catch (Exception e) {
            log.error("save cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
            log.warn("redis 保存数据出现情况，现发起一次重复连接", e);
            if (connection != null)
                connection.close();
            connection = redisConnectionFactory.getConnection();
            connection.setEx(cacheKey.getBytes(), timeoutSeconds, toSavedValue.getBytes());
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    public String getFromCache(CacheKeyConstants prefix, String... keys) {
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
//            shardedJedis = redisPool.instance();
            String value =   new String(connection.get(cacheKey.getBytes()) );
            return value;
        } catch (Exception e) {
            log.error("get from cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
            return null;
        } finally {
            if (connection != null)
                connection.close();
//            redisPool.safeClose(shardedJedis);
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }
}
