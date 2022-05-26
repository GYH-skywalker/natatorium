package com.example.natatorium.service;

import com.example.natatorium.common.PageParams;
import com.example.natatorium.entity.Course;
import com.example.natatorium.common.base.BaseIService;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【course】的数据库操作Service
* @createDate 2022-05-01 18:48:56
*/
public interface ICourseService extends BaseIService<Course> {
    List<Course> UCPage(PageParams params, Integer id);
}
