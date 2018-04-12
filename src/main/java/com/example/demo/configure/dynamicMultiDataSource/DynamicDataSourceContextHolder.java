package com.example.demo.configure.dynamicMultiDataSource;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {


    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "ds1";

    public static void setDataSource(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }


}
