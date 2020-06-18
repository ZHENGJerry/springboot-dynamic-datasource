package com.example.demo.config.aspect;

import com.example.demo.config.DynamicDataSource;
import com.example.demo.config.annotation.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class DataSourceAspect {

    @Autowired
    private DynamicDataSource dynamicDataSource;


    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) {

        Object[] args = point.getArgs();
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Method method = methodSignature.getMethod();
        System.out.println("---------------参数列表开始-------------------------");
        for (int i =0 ,len=parameterNames.length;i < len ;i++){
            System.out.println("参数名："+ parameterNames[i] + " = " +args[i]);
        }
        System.out.println("---------------参数列表结束-------------------------");
        String dsIp = (String) args[0];
        if (dsIp != null) {
            dynamicDataSource.setCurrentThreadDataSource(dsIp);
        }

    }
    
    @After("@annotation(ds)")
    public void afterExecute(JoinPoint point, TargetDataSource ds) {
        DynamicDataSource.clearCurrentDataSourceKey();
    }

}
