package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.menus;
import com.example.natatorium.mapper.MenuMapper;
import com.example.natatorium.service.ImenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class menuServiceImpl extends BaseServiceImpl<MenuMapper, menus> implements ImenuService {

    public List<menus> findMenusByRoleId(Integer roleId){
        List<menus> roleMenus = baseMapper.selectMenusByRole(roleId);
        return  menusFilter(roleMenus);
    }

    public List<menus> findAllMenusTree(){
        LambdaQueryWrapper<menus> lqw = new LambdaQueryWrapper<>();
        lqw.eq(menus::getFunctionMarks,"menu");
        return menusFilter(list(lqw));
    }

    @Override
    public List<String> findPerms(int roleId) {
        return baseMapper.selectPermsByRole(roleId);
    }

    protected List<menus> menusFilter(List<menus> roleMenus){
        List<menus> parentMenus = roleMenus.stream().filter(menu -> menu.getPid() == null || menu.getPid() == 0).collect(Collectors.toList());
        for (menus menu : parentMenus) {
            menu.setChildren(roleMenus.stream().filter(subMenu -> menu.getId().equals(subMenu.getPid())).collect(Collectors.toList()));
        }
        return parentMenus;
    }
}
