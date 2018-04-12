package com.example.demo.configure.dynamicMultiDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configurable
public class DataSourceConfig {

    private static final Logger logger= LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.second.url}")
    private String url2;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    //@Bean(name = "ds1")
    public DataSource dataSource1() {
        logger.info("=========>DataSourceConfig.dataSource1");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(10);
        druidDataSource.setMaxWait(6000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(0);
        return druidDataSource;
    }




    //@Bean(name = "ds2")
    public DataSource dataSource2() {
        logger.info("=========>DataSourceConfig.dataSource2");
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url2);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMaxActive(10);
        druidDataSource.setMaxWait(6000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(0);
        return druidDataSource;
    }


    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDS")
//    @Primary
    public DataSource dataSource() {
        logger.info("=========>DataSourceConfig.dataSource");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        DataSource ds1=dataSource1();
        dynamicDataSource.setDefaultTargetDataSource(ds1);

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put("ds1", ds1);
        dsMap.put("ds2", dataSource2());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
}
