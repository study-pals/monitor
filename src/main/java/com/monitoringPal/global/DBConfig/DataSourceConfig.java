package com.monitoringPal.global.DBConfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    public static final String META_DATASOURCE = "metaDataSource";
    public static final String DOMAIN_DATASOURCE = "domainDataSource";
    public static final String MONITOR_DATASOURCE = "monitorDataSource";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.meta")
    public HikariConfig metaHikariConfig() {
        return new HikariConfig();
    }

    @Bean(META_DATASOURCE)
    public DataSource metaDataSource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(metaHikariConfig()));
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.domain")
    public HikariConfig domainHikariConfig() {
        return new HikariConfig();
    }

    @Bean(DOMAIN_DATASOURCE)
    public DataSource domainDataSource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(domainHikariConfig()));
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.monitor")
    public HikariConfig monitorHikariConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean(MONITOR_DATASOURCE)
    public DataSource monitorDataSource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(monitorHikariConfig()));
    }
}
