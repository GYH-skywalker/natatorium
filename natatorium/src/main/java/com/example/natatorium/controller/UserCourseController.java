package com.example.natatorium.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.PageParams;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Course;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.UserCourse;
import com.example.natatorium.service.IUserCourseService;
import com.example.natatorium.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author GuYuHang
* @description 针对表【user_course】的Controller
* @createDate 2022-05-11 09:27:55
*/
@RestController
@RequestMapping("/UCourse")
public class UserCourseController extends BaseController<UserCourse> {


    @Autowired
    private IUserCourseService iUserCourseService;

    /**
     * 约课
     * */
    @PostMapping("/reservation/{courseId}")
    public Result reservation(@PathVariable int courseId, HttpServletRequest request){

        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            return Result.error(Constants.CODE_400,"当前未登录");
        }else{
            userDTO user = JWTUtil.getTokenInfo(token);
            UserCourse uc = new UserCourse();
            uc.setUserId(user.getId());
            uc.setCourseId(courseId);
            iUserCourseService.save(uc);
            return Result.success("添加成功!");
        }
    }
}