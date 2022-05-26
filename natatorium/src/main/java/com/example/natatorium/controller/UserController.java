package com.example.natatorium.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.common.LoginUser;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.users;
import com.example.natatorium.service.ImenuService;
import com.example.natatorium.service.IuserService;
import com.example.natatorium.utils.JWTUtil;
import com.example.natatorium.utils.MyExcelUtil;
import com.example.natatorium.utils.PasswordUtil;
import com.example.natatorium.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<users> {

    @Resource
    private IuserService iuserService;
//    @Resource
//    private ImenuService imenuService;

    @Autowired
    private RedisCache redisCache;

    @PostMapping("/register")
    public Result register(@RequestBody userDTO newUser){
        if(!StringUtils.hasText(newUser.getUserName())||!StringUtils.hasText(newUser.getPassword())){
            return Result.error(Constants.CODE_400,"用户名或密码不能为空!");
        }
        return Result.success(iuserService.register(newUser));
    }

    @PostMapping("/login")
    public Result login(@RequestBody userDTO loginUser){
        if(!StringUtils.hasText(loginUser.getUserName())||!StringUtils.hasText(loginUser.getPassword())){
            return Result.error(Constants.CODE_400,"用户名或密码不能为空!");
        }
        Map<String,Object> result = iuserService.login(loginUser);
        return Result.success(result);
    }

    @GetMapping("/getUserExcel")
    public void getUserExcel(HttpServletResponse response) throws IOException {
        List<users> userList = iuserService.list();
        MyExcelUtil.exportExcel(response,userList,"文件信息");
    }

    @PostMapping("/importExcel")
    public Result setUsers(MultipartFile file) throws IOException{
        List<users> userList = MyExcelUtil.importExcel(file,users.class);
        iuserService.saveBatch(userList);
        return Result.success();
    }

    @GetMapping("/getUserByPage")
    public Result getUserByPage(@RequestParam int currentPage,
                                @RequestParam int pageSize,
                                @RequestParam(defaultValue = "") String account){
        IPage<users> page = new Page<>(currentPage,pageSize);
        QueryWrapper<users> qw = new QueryWrapper<>();
        if(StringUtils.hasText(account)){
            qw.eq("user_name",account);
        }
        return Result.success(iuserService.page(page,qw));
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody userDTO userD){
        if(StringUtils.hasText(userD.getUserName())){
            if(userD.getRoleId() == 0){
                userD.setRoleId(2);
            }
            userD.setPassword("123456");
            return Result.success(iuserService.register(userD));
        }else{
            return Result.error(Constants.CODE_400,"用户名不能为空！");
        }
    }
    //还原用户密码为123456
    @PutMapping("restorePwd/{id}")
    public Result restorePwd(@PathVariable int id){
        UpdateWrapper<users> uw = new UpdateWrapper<>();
        String salt = PasswordUtil.salt();
        uw.set("password",PasswordUtil.EncryptPassword("123456",salt)).set("salt",salt).eq("id",id);
        return Result.success(iuserService.update(uw));
    }
}
