package com.example.natatorium.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Coach;
import com.example.natatorium.service.ICoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【coach(教练管理)】的Controller
* @createDate 2022-04-21 16:34:54
*/
@RestController
@RequestMapping("/coach")
public class CoachController extends BaseController<Coach> {

    @Autowired
    private ICoachService iCoachService;

    @GetMapping("/coachName")
    public Result getCoachName(){
        List<String> coachName = iCoachService.listCoachName();
        return Result.success(coachName);
    }
}