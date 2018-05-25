package com.boot.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = JedisProperties.JEDIS_PREFIX)
@Getter
@Setter
public class JedisProperties {

    public static final String JEDIS_PREFIX = "spring.redis";

    private String host;

    private int port;

    private String password;


    private int timeOut;

}
