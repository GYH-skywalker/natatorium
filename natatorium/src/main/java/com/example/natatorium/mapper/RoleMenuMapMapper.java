package com.example.natatorium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.natatorium.entity.roleMenuMap;

import java.util.List;

public interface RoleMenuMapMapper extends BaseMapper<roleMenuMap> {
    List<Integer> menuIdList(Integer roleId);
}
