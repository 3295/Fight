package com.example.demo.configure.dynamicMultiDataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * 2018年3月22日 16:40:43 xiaoguai
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
    private static final Logger logger= LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSource();
    }


}
