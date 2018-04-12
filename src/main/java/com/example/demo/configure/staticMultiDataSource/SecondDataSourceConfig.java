package com.example.demo.configure.staticMultiDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configurable //注册到springboot容器里去
@MapperScan(basePackages = "com.example.demo.dao.second",
        sqlSessionFactoryRef = "secondSqlSessionFactory")//扫描包
public class SecondDataSourceConfig {

    @Value("${spring.datasource.second.url}")
    private String url2;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;


    @Bean("secondDataSource")
    public DataSource secondDataSource() {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url2);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
//        druidDataSource.setInitialSize(5);
//        druidDataSource.setMinIdle(5);
//        //druidDataSource.setMaxActive(10);
//        druidDataSource.setMaxWait(6000);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(0);
//        druidDataSource.setTestWhileIdle(false);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
        return druidDataSource;
    }


    // MyBatis-Spring-Boot-Starter 使用了该Starter之后，
    // 只需要定义一个DataSource即可，它会自动创建使用该DataSource的SqlSessionFactoryBean以及SqlSessionTemplate。
    // 会自动扫描你的Mappers，连接到SqlSessionTemplate，并注册到Spring上下文中。


    @Bean("secondSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/**/*.xml"));
        return bean.getObject();
    }

    @Bean("secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
