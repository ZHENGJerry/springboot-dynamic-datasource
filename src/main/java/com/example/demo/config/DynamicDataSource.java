package com.example.demo.config;

import com.example.demo.entity.DatabaseDetail;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;


public class DynamicDataSource extends AbstractRoutingDataSource {

    /**TODO 暂定ip
     * 缓存当前线程数据源的key（IP）
     */
    private static final ThreadLocal<String> CURRENT_DATASOURCE_KEY = new ThreadLocal<>();
    /**
     * 缓存租户对应的数据源
     * ConcurrentHashMap<ip，数据源>
     */
    private ConcurrentHashMap<Object, Object> targetDataSources = new ConcurrentHashMap<>();


    public DynamicDataSource(DataSource defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
    }

    /**
     * 选择当前线程数据源的key
     */
    @Override
    public Object determineCurrentLookupKey() {
        return CURRENT_DATASOURCE_KEY.get();
    }

    /**
     * 清除当前线程数据源key
     */
    public static void clearCurrentDataSourceKey() {
        CURRENT_DATASOURCE_KEY.remove();
    }

    /**
     * 设置当前线程的数据源
     */
    public void setCurrentThreadDataSource(String dataSourceKey) {
        if (!targetDataSources.containsKey(dataSourceKey)) {
            addNewDataSource(dataSourceKey);
        }
        CURRENT_DATASOURCE_KEY.set(dataSourceKey);
    }

    private synchronized void addNewDataSource(String dataSourceKey) {
        if (targetDataSources.containsKey(dataSourceKey)) {
            return;
        }
        DataSource datasource = createDataSource(dataSourceKey);
        targetDataSources.put(dataSourceKey, datasource);
        super.afterPropertiesSet();
    }


    private DataSource createDataSource(String dataSourceKey) {
        //todo 需要修改为通过解析warehouse定位的ip进行数据库连接的相关封装
        DatabaseDetail dbDetail = new DatabaseDetail();
        if("2".equals(dataSourceKey)){
            dbDetail.setDriverClassName("org.postgresql.Driver");
            dbDetail.setUrl("jdbc:postgresql://127.0.0.1:5432/test");
            dbDetail.setUsername("postgres");
            dbDetail.setPassword("123456");
        }else{
            dbDetail.setDriverClassName("org.postgresql.Driver");
            dbDetail.setUrl("jdbc:postgresql://127.0.0.1:5432/postgres");
            dbDetail.setUsername("postgres");
            dbDetail.setPassword("123456");
        }
        return DynamicDataSourceConfig.createDataSource(dbDetail);
    }




}
