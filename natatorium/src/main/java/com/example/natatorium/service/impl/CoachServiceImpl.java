package com.example.natatorium.service.impl;

import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.Coach;
import com.example.natatorium.service.ICoachService;
import com.example.natatorium.mapper.CoachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【coach(教练管理)】的数据库操作Service实现
* @createDate 2022-04-21 16:34:54
*/
@Service
public class CoachServiceImpl extends BaseServiceImpl<CoachMapper, Coach>
    implements ICoachService{

    @Autowired
    private CoachMapper coachMapper;

    @Override
    public List<String> listCoachName() {
        List<String> coachName = coachMapper.getAllCoachName();
        return coachName;
    }
}




