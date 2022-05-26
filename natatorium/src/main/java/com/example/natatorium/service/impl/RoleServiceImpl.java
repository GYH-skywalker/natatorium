package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.Role;
import com.example.natatorium.service.IRoleService;
import com.example.natatorium.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2022-04-12 20:00:39
*/
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role>
    implements IRoleService{

}




