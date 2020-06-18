package com.example.demo.config.annotation;

import java.lang.annotation.*;


@Target({ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
}
