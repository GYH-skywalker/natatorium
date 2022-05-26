package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.LoginUser;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.common.nataAuthenticationProvider;
import com.example.natatorium.entity.Consumer;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.users;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.service.IConsumerService;
import com.example.natatorium.mapper.ConsumerMapper;
import com.example.natatorium.utils.JWTUtil;
import com.example.natatorium.utils.PasswordUtil;
import com.example.natatorium.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author GuYuHang
* @description 针对表【sys_consumer】的数据库操作Service实现
* @createDate 2022-04-29 21:39:10
*/
@Service
public class ConsumerServiceImpl extends BaseServiceImpl<ConsumerMapper, Consumer>
    implements IConsumerService{

    @Autowired
    private nataAuthenticationProvider authenticationProvider;
    @Autowired
    private RedisCache redisCache;

    @Override
    public Map<String, Object> login(userDTO userDTO) {
        QueryWrapper<Consumer> qw = new QueryWrapper<>();
        qw.eq("phone",userDTO.getPhone());
        Consumer cu = getOne(qw);
        if(cu == null){
            cu = new Consumer();
            BeanUtils.copyProperties(userDTO,cu);
            String salt = PasswordUtil.salt();
            cu.setSalt(salt);
            cu.setRoleId(4);
            cu.setCreateTime(new Timestamp(new Date().getTime()).toString());
            cu.setPassword(PasswordUtil.EncryptPassword("123456",salt));
            save(cu);
        }
        return this.verify(userDTO);
    }


    private Map<String,Object> verify(userDTO userDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDTO, userDTO.getPassword());
        Authentication authentication = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        if (Objects.isNull(authentication)) {
            System.out.println("tests");
            throw new serviceException(Constants.CODE_400, "登录失败");
        } else {
            LoginUser lu = (LoginUser) authentication.getPrincipal();
            Consumer user = lu.getConsumer();
            Map<String, Object> result = new HashMap<>();
            result.put("token", JWTUtil.getToken(user));
            System.out.println(result);
            redisCache.setCacheObject("mobile:login:" + user.getId(), lu);
            return result;
        }
    }




//        QueryWrapper<Consumer> qw = new QueryWrapper<>();
//        qw.eq("phone",loginUser.getPhone());
//        Consumer cu = getOne(qw);
//        if(cu == null){
////            throw new serviceException(Constants.CODE_400,"当前用户不存在!");
//            cu = new Consumer();
//            BeanUtils.copyProperties(loginUser,cu);
//            String salt = PasswordUtil.salt();
//            cu.setSalt(salt);
//            cu.setPassword(PasswordUtil.EncryptPassword("123456",salt));
//            save(cu);
//            return cu;
//        }else{
//            if(cu.getEnable() == 1){
//                Boolean isPass = PasswordUtil.passwordVerify(loginUser.getPassword(),cu.getSalt(),cu.getPassword());
//                if (isPass) {
//                    return cu;
//                } else {
//                    throw new serviceException(Constants.CODE_400,"密码错误!");
//                }
//            }else{
//                throw new serviceException(Constants.CODE_600,"当前用户已被禁用!");
//            }
//        }
//    }

}




