package com.example.natatorium.mapper;

import com.example.natatorium.entity.Notify;
import com.example.natatorium.common.base.BaseIMapper;

/**
* @author GuYuHang
* @description 针对表【notify】的数据库操作Mapper
* @createDate 2022-05-13 10:31:56
* @Entity com.example.natatorium.entity.Notify
*/
public interface NotifyMapper extends BaseIMapper<Notify> {

    Notify getLatest();
}




