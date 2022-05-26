package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.natatorium.entity.roleMenuMap;
import com.example.natatorium.mapper.RoleMenuMapMapper;
import com.example.natatorium.service.IroleMenuMapService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class roleMenuMapImpl extends ServiceImpl<RoleMenuMapMapper, roleMenuMap> implements IroleMenuMapService {
    @Override
    public String setRoleMenuMap(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<roleMenuMap> qw = new QueryWrapper<>();
        qw.eq("role_id",roleId);
        remove(qw);
        for (Integer menuId : menuIds) {
            roleMenuMap RMM = new roleMenuMap();
            RMM.setRoleId(roleId);
            RMM.setMenuId(menuId);
            save(RMM);
        }
        return "分配成功！";
    }

    @Override
    public List<Integer> listMenuId(Integer roleId) {

        return baseMapper.menuIdList(roleId);
    }
}
