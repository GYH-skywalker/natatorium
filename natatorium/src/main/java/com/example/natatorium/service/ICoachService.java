package com.example.natatorium.service;

import com.example.natatorium.entity.Coach;
import com.example.natatorium.common.base.BaseIService;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【coach(教练管理)】的数据库操作Service
* @createDate 2022-04-21 16:34:54
*/
public interface ICoachService extends BaseIService<Coach> {

    List<String> listCoachName();
}
