package com.example.natatorium.service;

import com.example.natatorium.entity.Notify;
import com.example.natatorium.common.base.BaseIService;

/**
* @author GuYuHang
* @description 针对表【notify】的数据库操作Service
* @createDate 2022-05-13 10:31:56
*/
public interface INotifyService extends BaseIService<Notify> {

    Notify Latest();
}
