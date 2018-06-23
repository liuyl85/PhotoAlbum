package com.yun.album.common;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Druid数据库连接池
 */
@Configuration
public class DruidConfig {

    @Autowired
    @Bean
    public DataSource druidDataSource(DruidProperties druidProperties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(druidProperties.getUrl());
        datasource.setUsername(druidProperties.getUsername());
        datasource.setPassword(druidProperties.getPassword());
        datasource.setDriverClassName(druidProperties.getDriverClassName());
        datasource.setInitialSize(druidProperties.getInitialSize());
        datasource.setMinIdle(druidProperties.getMinIdle());
        datasource.setMaxActive(druidProperties.getMaxActive());
        datasource.setMaxWait(druidProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(druidProperties.getValidationQuery());
        datasource.setTestWhileIdle(druidProperties.isTestWhileIdle());
        datasource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        datasource.setTestOnReturn(druidProperties.isTestOnReturn());
        return datasource;
    }
}
