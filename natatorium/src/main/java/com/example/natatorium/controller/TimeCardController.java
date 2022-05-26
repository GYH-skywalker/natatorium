package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.TimeCard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【time_card(期限卡种类)】的Controller
* @createDate 2022-04-20 14:00:12
*/
@RestController
@RequestMapping("/tc")
public class TimeCardController extends BaseController<TimeCard> {

}