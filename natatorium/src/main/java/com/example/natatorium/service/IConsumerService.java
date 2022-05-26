package com.example.natatorium.service;

import com.example.natatorium.entity.Consumer;
import com.example.natatorium.common.base.BaseIService;
import com.example.natatorium.entity.DTO.userDTO;

import java.util.Map;

/**
* @author GuYuHang
* @description 针对表【sys_consumer】的数据库操作Service
* @createDate 2022-04-29 21:39:10
*/
public interface IConsumerService extends BaseIService<Consumer> {
    Map<String, Object> login(userDTO loginUser);
}
