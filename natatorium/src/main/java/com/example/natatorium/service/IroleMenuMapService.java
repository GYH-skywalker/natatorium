package com.example.natatorium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.natatorium.entity.roleMenuMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IroleMenuMapService extends IService<roleMenuMap> {
    String setRoleMenuMap(Integer roleId, List<Integer> menuIds);

    List<Integer> listMenuId(@Param("roleId") Integer roleId);
}
