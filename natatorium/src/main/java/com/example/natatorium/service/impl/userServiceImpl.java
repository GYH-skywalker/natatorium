package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.LoginUser;
import com.example.natatorium.common.nataAuthenticationProvider;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.menus;
import com.example.natatorium.entity.users;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.mapper.UserMapper;
import com.example.natatorium.service.ImenuService;
import com.example.natatorium.service.IuserService;
import com.example.natatorium.utils.JWTUtil;
import com.example.natatorium.utils.PasswordUtil;
import com.example.natatorium.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Service
public class userServiceImpl extends ServiceImpl<UserMapper, users> implements IuserService {

    @Resource
    private ImenuService imenuService;

    @Autowired
    private RedisCache redisCache;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private nataAuthenticationProvider authenticationProvider;

    @Override
    public String register(userDTO newUser){
        QueryWrapper<users> qw = new QueryWrapper<>();
        qw.eq("user_name",newUser.getUserName());
        users u = getOne(qw);
        if(u == null){
            u = new users();
            BeanUtils.copyProperties(newUser,u);
            String salt = PasswordUtil.salt();
            u.setPassword(PasswordUtil.EncryptPassword(u.getPassword(),salt));
            u.setSalt(salt);
            Timestamp createTime = new Timestamp(new Date().getTime());
            u.setCreateTime(createTime.toString());
            u.setRoleId(2);
            save(u);
        }else{
            throw new serviceException(Constants.CODE_400,"用户已存在!");
        }
        return "注册成功!";
    }


    @Override
    public Map<String, Object> login(userDTO userDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDTO,userDTO.getPassword());
        Authentication authentication = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authentication)){
            throw new serviceException(Constants.CODE_400,"登录失败");
        }else {
            LoginUser lu = (LoginUser) authentication.getPrincipal();
            users user = lu.getUser();
            Map<String, Object> result = new HashMap<>();
            List<menus> menus = imenuService.findMenusByRoleId(user.getRoleId());
            result.put("token", JWTUtil.getToken(user));
            result.put("menus",menus);
            redisCache.setCacheObject("pc:login:"+user.getId(),lu);
            return result;
        }
//        if(Objects.isNull(u)){
//            throw new serviceException(Constants.CODE_400,"当前用户不存在!");
//        }else{
//            if(u.getEnable() == 1){
//                Boolean isPass = PasswordUtil.passwordVerify(loginUser.getPassword(),u.getSalt(),u.getPassword());
//                if (isPass) {
//                    return u;
//                } else {
//                    throw new serviceException(Constants.CODE_400,"密码错误!");
//                }
//            }else{
//                throw new serviceException(Constants.CODE_600,"当前用户已被禁用!");
//            }
//        }
    }
}
