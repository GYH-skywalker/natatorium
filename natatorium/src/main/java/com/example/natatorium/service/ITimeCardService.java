package com.example.natatorium.service;

import com.example.natatorium.entity.TimeCard;
import com.example.natatorium.common.base.BaseIService;

/**
* @author GuYuHang
* @description 针对表【time_card(期限卡种类)】的数据库操作Service
* @createDate 2022-04-20 14:00:12
*/
public interface ITimeCardService extends BaseIService<TimeCard> {

    int updateCounts(String packageName);
}
