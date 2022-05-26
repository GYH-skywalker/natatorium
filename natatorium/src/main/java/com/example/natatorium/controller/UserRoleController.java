package com.example.natatorium.controller;

import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.UserRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【user_role】的Controller
* @createDate 2022-04-19 14:06:22
*/
@RestController
@RequestMapping("/userRole")
public class UserRoleController extends BaseController<UserRole> {

}