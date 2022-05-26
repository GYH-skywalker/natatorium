package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.TicketSetting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【ticket_setting】的Controller
* @createDate 2022-04-21 21:55:30
*/
@RestController
@RequestMapping("/ts")
public class TicketSettingController extends BaseController<TicketSetting> {

}