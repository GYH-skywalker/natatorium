package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.BusinessControl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【business_control(业务总控)】的Controller
* @createDate 2022-04-20 09:27:44
*/
@RestController
@RequestMapping("/bc")
public class BusinessControlController extends BaseController<BusinessControl> {

}