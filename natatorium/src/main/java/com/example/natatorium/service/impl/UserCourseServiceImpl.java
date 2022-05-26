package com.example.natatorium.service.impl;

import com.example.natatorium.common.PageParams;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.Course;
import com.example.natatorium.entity.UserCourse;
import com.example.natatorium.service.IUserCourseService;
import com.example.natatorium.mapper.UserCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【user_course】的数据库操作Service实现
* @createDate 2022-05-11 09:27:55
*/
@Service
public class UserCourseServiceImpl extends BaseServiceImpl<UserCourseMapper, UserCourse>
    implements IUserCourseService{

    @Autowired
    private UserCourseMapper userCourseMapper;


}




