package com.example.natatorium.service;

import com.example.natatorium.common.base.BaseIService;
import com.example.natatorium.entity.menus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImenuService extends BaseIService<menus> {
    List<menus> findMenusByRoleId(Integer roleId);
    List<menus> findAllMenusTree();
    List<String> findPerms(int roleId);
}
