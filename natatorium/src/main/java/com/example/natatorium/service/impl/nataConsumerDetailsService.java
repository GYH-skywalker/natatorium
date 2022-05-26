package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.LoginUser;
import com.example.natatorium.entity.Consumer;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.menus;
import com.example.natatorium.entity.users;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.mapper.ConsumerMapper;
import com.example.natatorium.mapper.UserMapper;
import com.example.natatorium.service.ImenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class nataConsumerDetailsService implements UserDetailsService {
    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private ImenuService imenuService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        LambdaQueryWrapper<Consumer> qw = new LambdaQueryWrapper<>();
        qw.eq(Consumer::getPhone,phone);
        Consumer c = consumerMapper.selectOne(qw);
        if(Objects.isNull(c)){
            throw new serviceException(Constants.CODE_400,"当前用户不存在!");
        }
        List<String> perms = imenuService.findPerms(c.getRoleId());
        return new LoginUser(c,perms);
    }
}
