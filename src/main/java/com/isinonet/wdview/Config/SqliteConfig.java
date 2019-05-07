package com.isinonet.wdview.Config;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * Created by zouchen on 2019/4/21.
 */
@Configuration
public class SqliteConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;

    private static String MYBATIS_CONFIG = "mybatis-config.xml";

    @Bean(name="dataSource")
    public DataSource dataSource() {
//        SQLiteDataSource dataSource = new SQLiteDataSource();
//        dataSource.setUrl(dataSourceProperties.getUrl());
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setMaxActive(1);
        dataSource.setMinIdle(0);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
