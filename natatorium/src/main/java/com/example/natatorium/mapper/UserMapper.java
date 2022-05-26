package com.example.natatorium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.natatorium.entity.users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<users> {
}
