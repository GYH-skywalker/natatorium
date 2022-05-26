package com.example.natatorium.mapper;

import com.example.natatorium.entity.Coach;
import com.example.natatorium.common.base.BaseIMapper;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【coach(教练管理)】的数据库操作Mapper
* @createDate 2022-04-21 16:34:54
* @Entity com.example.natatorium.entity.Coach
*/
public interface CoachMapper extends BaseIMapper<Coach> {

    List<String> getAllCoachName();
}




