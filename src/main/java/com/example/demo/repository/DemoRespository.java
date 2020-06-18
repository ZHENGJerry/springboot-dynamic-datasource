package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DemoRespository extends JpaRepository<User, String> {
    @Query(value = "select * from user_info ", nativeQuery = true)
    List<Map<String, String>> listUser();
}