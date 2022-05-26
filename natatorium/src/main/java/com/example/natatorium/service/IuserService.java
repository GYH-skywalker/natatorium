package com.example.natatorium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.users;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IuserService extends IService<users> {
    String register(userDTO user);

    Map<String,Object> login(userDTO loginUser);
}
