package com.example.natatorium.service;

import com.example.natatorium.entity.BusinessCardList;
import com.example.natatorium.common.base.BaseIService;

import java.util.Map;

/**
* @author GuYuHang
* @description 针对表【business_card_list】的数据库操作Service
* @createDate 2022-05-08 19:55:51
*/
public interface IBusinessCardListService extends BaseIService<BusinessCardList> {

    Map<String, Object> Statistic(String start, String end);
}
