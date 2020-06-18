package com.example.demo.controller;

import com.example.demo.config.annotation.TargetDataSource;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{ip}")
    @TargetDataSource
    public List<Map<String, String>> getAll(@PathVariable String ip) {
        return userService.selectAll();
    }

    @GetMapping("/user2/{ip}")
    @TargetDataSource
    public List<Map<String, String>> getAll2(@PathVariable String ip) {
        return userService.selectAll2();
    }


}
