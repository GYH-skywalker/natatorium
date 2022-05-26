package com.example.natatorium.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.natatorium.common.Constants;
import com.example.natatorium.entity.Consumer;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.users;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.service.IConsumerService;
import com.example.natatorium.service.IuserService;
import com.example.natatorium.service.impl.ConsumerServiceImpl;
import com.example.natatorium.service.impl.userServiceImpl;
import javassist.expr.Instanceof;
import org.springframework.util.StringUtils;

import java.util.Calendar;

public class JWTUtil {

    private static IuserService iuserService = SpringUtil.getBean(userServiceImpl.class);
    private static ConsumerServiceImpl iconsumerService = SpringUtil.getBean(ConsumerServiceImpl.class);

    public static String getToken(users user){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7);//标识从当前日期顺延7天
        JWTCreator.Builder jwtBuilder = JWT.create();
        String token = jwtBuilder.withClaim("username",user.getUserName())
                .withClaim("id",user.getId())
                .withClaim("roleId",user.getRoleId())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    public static String getToken(Consumer consumer){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7);//标识从当前日期顺延7天
        JWTCreator.Builder jwtBuilder = JWT.create();
        String token = jwtBuilder.withClaim("phone",consumer.getPhone())
                .withClaim("id",consumer.getId())
                .withClaim("roleId",consumer.getRoleId())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(consumer.getPassword()));
        return token;
    }

    //token验证
    public static DecodedJWT verify(String token,String platform) throws Exception{
        if(!StringUtils.hasText(token)){
            throw new serviceException(Constants.CODE_401,"token is empty");
        }
        DecodedJWT decodedJWT = JWT.decode(token);
        Claim claim = decodedJWT.getClaim("id");
        String password = findPassword(claim.asInt(),platform);
        System.out.println(password);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(password)).build();
        return  verifier.verify(token);
    }

    //找回密码
    private static String findPassword(int id,String platform){
        System.out.println("jwtutil"+platform);
        if(Constants.PC.equals(platform)){
            QueryWrapper<users> qw = new QueryWrapper<>();
            qw.eq("id",id);
            users one = iuserService.getOne(qw);
            if(one != null){
                return one.getPassword();
            }else {
                throw new serviceException(Constants.CODE_401,"当前用户不存在");
            }
        }else{
            QueryWrapper<Consumer> qw = new QueryWrapper<>();
            qw.eq("id",id);
            Consumer one = iconsumerService.getOne(qw);
            if(one != null){
                return one.getPassword();
            }else {
                throw new serviceException(Constants.CODE_401,"当前用户不存在");
            }
        }
    }

    public static userDTO getTokenInfo(String token){
        if(!StringUtils.hasText(token)){
            throw new serviceException(Constants.CODE_401,"token is empty");
        }
        DecodedJWT decodedJWT = JWT.decode(token);
        userDTO user = new userDTO();
        user.setId(decodedJWT.getClaim("id").asInt());
        user.setPhone(decodedJWT.getClaim("phone").asString());
        user.setUserName(decodedJWT.getClaim("username").asString());
//        user.setRoleId();
        return user;
    }
}
