package com.boot.configs;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Slf4j
public class DataSourceConfiguration {



    @Bean
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean()  throws IOException{
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());

//        Resource[] resources = new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:com/example/demo3/mapper/*Mapper.xml");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:com/boot/security/dao/mapper/*.xml"));
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
//        sqlSessionFactory.setMapperLocations(resources);

            return sqlSessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
