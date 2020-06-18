package com.example.demo.bussiness;

import com.example.demo.config.annotation.TargetDataSource;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author 作者信息
 * @date 2020-06-13
 */
@Component
public class DoService {
    @Autowired
    UserService userService;

    @TargetDataSource
    public List<Map<String, String>> getAll(String ip) {
        System.out.println("ip:"+ ip);
//        userService = SpringUtil.getBean(UserService.class);
        return userService.selectAll();
    }
}
