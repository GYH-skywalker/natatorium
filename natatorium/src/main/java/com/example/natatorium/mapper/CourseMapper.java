package com.example.natatorium.mapper;

import com.example.natatorium.common.PageParams;
import com.example.natatorium.entity.Course;
import com.example.natatorium.common.base.BaseIMapper;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【course】的数据库操作Mapper
* @createDate 2022-05-01 18:48:56
* @Entity com.example.natatorium.entity.Course
*/
public interface CourseMapper extends BaseIMapper<Course> {

    List<Course> ucPage(Integer id, Integer currentPage, Integer pageSize, Integer enable);
}




