package com.example.natatorium.controller;

import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Role;
import com.example.natatorium.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {

    @Autowired
    private IRoleService iRoleService;

    @GetMapping("/allRole")
    public Result getAllRole(){
        return Result.success(iRoleService.list());
    }
}
