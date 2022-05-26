package com.example.natatorium.controller;

import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.service.IRoleService;
import com.example.natatorium.service.IroleMenuMapService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/roleMenus")
public class RoleMenuMapController {

    @Resource
    private IroleMenuMapService iroleMenuMapService;

    @Resource
    private IRoleService iRoleService;

    @PostMapping("/assignMenu/{roleId}")
    public Result assignMenu(@PathVariable Integer roleId,
                             @RequestBody List<Integer> menuIds){
        if (iRoleService.getById(roleId) != null) {
            return Result.success(iroleMenuMapService.setRoleMenuMap(roleId,menuIds));
        } else {
            return Result.error(Constants.CODE_400,"该角色不存在!");
        }

    }

    @GetMapping("/getRoleMenuMap/{roleId}")
    public Result getRoleMenuMap(@PathVariable Integer roleId){
        return Result.success(iroleMenuMapService.listMenuId(roleId));
    }
}
