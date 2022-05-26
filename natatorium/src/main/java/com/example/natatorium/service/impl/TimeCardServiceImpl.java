package com.example.natatorium.service.impl;

import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.TimeCard;
import com.example.natatorium.service.ITimeCardService;
import com.example.natatorium.mapper.TimeCardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author GuYuHang
* @description 针对表【time_card(期限卡种类)】的数据库操作Service实现
* @createDate 2022-04-20 14:00:12
*/
@Service
public class TimeCardServiceImpl extends BaseServiceImpl<TimeCardMapper, TimeCard>
    implements ITimeCardService{

    @Autowired
    private TimeCardMapper timeCardMapper;
    @Override
    public int updateCounts(String packageName) {
        return timeCardMapper.modifyCounts(packageName);
    }
}




