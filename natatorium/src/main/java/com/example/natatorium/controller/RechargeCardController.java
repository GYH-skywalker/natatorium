package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.RechargeCard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【recharge_card(充值卡)】的Controller
* @createDate 2022-04-20 16:00:58
*/
@RestController
@RequestMapping("/rcc")
public class RechargeCardController extends BaseController<RechargeCard> {

}