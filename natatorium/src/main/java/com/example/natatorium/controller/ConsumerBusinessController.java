package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.ConsumerBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【consumer_business(消费业务)】的Controller
* @createDate 2022-05-01 18:35:10
*/
@RestController
@RequestMapping("cb")
public class ConsumerBusinessController extends BaseController<ConsumerBusiness> {

}