package com.example.natatorium.service.impl;

import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.Notify;
import com.example.natatorium.service.INotifyService;
import com.example.natatorium.mapper.NotifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author GuYuHang
* @description 针对表【notify】的数据库操作Service实现
* @createDate 2022-05-13 10:31:56
*/
@Service
public class NotifyServiceImpl extends BaseServiceImpl<NotifyMapper, Notify>
    implements INotifyService{

    @Autowired
    private NotifyMapper notifyMapper;

    @Override
    public Notify Latest() {
        return notifyMapper.getLatest();
    }
}




