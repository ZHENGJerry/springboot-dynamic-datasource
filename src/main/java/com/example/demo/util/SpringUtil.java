package com.example.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author ruoyi
 */
@Configuration
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }
    public static Object getBeanByName(String beanName){
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(beanName);
    }
    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

}