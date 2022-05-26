package com.example.natatorium.mapper;

import com.example.natatorium.common.base.BaseIMapper;
import com.example.natatorium.entity.menus;

import java.util.List;

public interface MenuMapper extends BaseIMapper<menus> {
  List<menus> selectMenusByRole(Integer roleId);
  List<String> selectPermsByRole(int roleId);
}
