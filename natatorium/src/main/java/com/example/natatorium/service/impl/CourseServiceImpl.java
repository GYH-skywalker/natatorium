package com.example.natatorium.service.impl;

import com.example.natatorium.common.PageParams;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.Course;
import com.example.natatorium.service.ICourseService;
import com.example.natatorium.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【course】的数据库操作Service实现
* @createDate 2022-05-01 18:48:56
*/
@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseMapper, Course>
    implements ICourseService{

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> UCPage(PageParams params, Integer id) {
        int enable = 1;
        if(params.getMultiParams() != null){
            enable = (Integer) params.getMultiParams().get("enable");
        }
        int currentPage = (params.getCurrentPage() - 1) * params.getPageSize();
        List<Course> ucs =
                courseMapper.ucPage(id,currentPage,params.getPageSize(),enable);
        return ucs;
    }
}




