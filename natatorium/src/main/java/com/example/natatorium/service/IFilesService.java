package com.example.natatorium.service;

import com.example.natatorium.entity.Files;
import com.example.natatorium.common.base.BaseIService;

/**
* @author GuYuHang
* @description 针对表【files】的数据库操作Service
* @createDate 2022-04-14 14:15:54
*/
public interface IFilesService extends BaseIService<Files> {

    Files findFIlePathByUUID(String uuid);

    boolean del(String uuid);
}
