package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.LoginUser;
import com.example.natatorium.entity.menus;
import com.example.natatorium.entity.users;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.mapper.UserMapper;
import com.example.natatorium.service.ImenuService;
import com.example.natatorium.service.nataUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class nataUserDetailsServiceImpl implements nataUserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ImenuService imenuService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LambdaQueryWrapper<users> qw = new LambdaQueryWrapper<>();
        qw.eq(users::getUserName,userName);
        users u = userMapper.selectOne(qw);
        if(Objects.isNull(u)){
            throw new serviceException(Constants.CODE_400,"当前用户不存在!");
        }
//        List<menus> menus = imenuService.findMenusByRoleId(u.getRoleId());
        List<String> perms = imenuService.findPerms(u.getRoleId());
        return new LoginUser(u,perms);
    }
}
