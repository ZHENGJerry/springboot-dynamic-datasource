package com.example.demo.service;

import com.example.demo.repository.DemoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    DemoRespository demoRepository;

    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, String>> selectAll() {
        List<Map<String, String>> maps = demoRepository.listUser();
        return maps;
    }
    @Transactional(rollbackFor = Exception.class)
    public List<Map<String, String>> selectAll2() {
        return demoRepository.listUser();
    }
}
