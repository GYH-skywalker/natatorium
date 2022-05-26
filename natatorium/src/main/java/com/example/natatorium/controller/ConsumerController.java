package com.example.natatorium.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Consumer;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.basicMemberInfo;
import com.example.natatorium.entity.users;
import com.example.natatorium.service.IConsumerService;
import com.example.natatorium.service.IbasicMemberInfoService;
import com.example.natatorium.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
* @author GuYuHang
* @description 针对表【sys_consumer】的Controller
* @createDate 2022-04-29 21:39:10
*/
@RestController
@RequestMapping("/consumer")
public class ConsumerController extends BaseController<Consumer> {

    @Autowired
    private IConsumerService iConsumerService;

    @Autowired
    private IbasicMemberInfoService ibasicMemberInfoService;

    @PostMapping("/login")
    public Result login(@RequestBody userDTO loginUser){
        if(!StringUtils.hasText(loginUser.getPhone())||!StringUtils.hasText(loginUser.getPassword())){
            return Result.error(Constants.CODE_400,"用户名或密码不能为空!");
        }

        QueryWrapper<basicMemberInfo> qw = new QueryWrapper<>();
        qw.eq("card_user_phone",loginUser.getPhone());
        basicMemberInfo bmi = ibasicMemberInfoService.getOne(qw);
        if(bmi == null){
            return Result.error(Constants.CODE_400,"会员未注册");
        }else{
            Map<String, Object> result = iConsumerService.login(loginUser);
            System.out.println(result);
            return Result.success(result);
        }

    }

}