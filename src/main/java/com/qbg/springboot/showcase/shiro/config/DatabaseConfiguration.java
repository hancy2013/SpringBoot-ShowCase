package com.qbg.springboot.showcase.shiro.config;

import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RelaxedPropertyResolver datasourcePropertyResolver;
    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        this.datasourcePropertyResolver = new RelaxedPropertyResolver(env,
                "spring.datasource.");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        logger.debug("Configuring DataSource");
        if (StringUtils.isEmpty(datasourcePropertyResolver.getProperty("url"))) {
            logger.error(
                    "Your database connection pool configuration is incorrect! Please check your Spring profile, current profiles are:{}",
                    Arrays.toString(env.getActiveProfiles()));
            throw new ApplicationContextException(
                    "Database connection pool is not configured correctly");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(datasourcePropertyResolver.getProperty("url"));
        druidDataSource.setUsername(datasourcePropertyResolver
                .getProperty("username"));
        druidDataSource.setPassword(datasourcePropertyResolver
                .getProperty("password"));
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(20);
        // 获取连接等待超时的时间
        druidDataSource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT　'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setFilters("config");
        druidDataSource.setConnectionProperties("config.decrypt=true");
        return druidDataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }
}
