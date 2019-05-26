package com.mondari.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /**
     * 使用 Spring Boot 2.x 默认数据源 HikariCP
     * @param properties
     * @return
     */
//    @Bean
//    public DataSource dataSource(DataSourceProperties properties) {
//        return DataSourceBuilder.create()
//                .url(properties.getUrl())
//                .username(properties.getUsername())
//                .password(properties.getPassword())
//                .build();
//    }

    /**
     * 使用 Alibaba Druid 数据源
     *
     * @return
     */
    @Bean
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 多数据源配置
     */
//    @Primary
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.one")
//    public DataSource dataSourceOne(){
//        return DruidDataSourceBuilder.create().build();
//    }
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.two")
//    public DataSource dataSourceTwo(){
//        return DruidDataSourceBuilder.create().build();
//    }

}
