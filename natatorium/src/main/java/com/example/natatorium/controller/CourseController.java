package com.example.natatorium.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.PageParams;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Course;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.service.ICourseService;
import com.example.natatorium.service.IFilesService;
import com.example.natatorium.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author GuYuHang
* @description 针对表【course】的Controller
* @createDate 2022-05-01 18:48:56
*/
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController<Course> {

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IFilesService iFilesService;

    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody Course course){

        if (iCourseService.save(course)) {
            return Result.success();
        } else {
            iFilesService.del(course.getCourseCover());
            return Result.error(Constants.CODE_500,"添加失败");
        }

    }

    @DeleteMapping("/delCourse/{id}")
    public Result delCourse(@PathVariable int id){
        Course course = iCourseService.getById(id);
        if(course != null){
            if (iCourseService.removeById(id)) {
                iFilesService.del(course.getCoverUuid());
                return Result.success();
            } else {
                return Result.error(Constants.CODE_500,"系统错误,删除失败,请联系管理员");
            }
        }else{
            return Result.error(Constants.CODE_400,"该课程不存在");
        }
    }


    @PostMapping("/myCourse")
    public Result getMyCourse(@RequestBody PageParams params, HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.hasText(token)){
            List<Course> myCourses = iCourseService.UCPage(params,JWTUtil.getTokenInfo(token).getId());
            return Result.success(myCourses);
        }else{
            return Result.error(Constants.CODE_400,"当前未登录");
        }
    }

}