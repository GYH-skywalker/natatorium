package com.example.natatorium.mapper;

import com.example.natatorium.entity.Files;
import com.example.natatorium.common.base.BaseIMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author GuYuHang
* @description 针对表【files】的数据库操作Mapper
* @createDate 2022-04-14 14:15:54
* @Entity com.example.natatorium.entity.Files
*/
public interface FilesMapper extends BaseIMapper<Files> {

     Files findPathAndName(@Param("uuid") String uuid);
}




