package com.example.natatorium.service.impl;

import com.example.natatorium.common.base.BaseServiceImpl;
import com.example.natatorium.entity.BusinessCardList;
import com.example.natatorium.entity.VO.LineChart;
import com.example.natatorium.entity.VO.NightingaleChart;
import com.example.natatorium.service.IBusinessCardListService;
import com.example.natatorium.mapper.BusinessCardListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author GuYuHang
* @description 针对表【business_card_list】的数据库操作Service实现
* @createDate 2022-05-08 19:55:51
*/
@Service
public class BusinessCardListServiceImpl extends BaseServiceImpl<BusinessCardListMapper, BusinessCardList>
    implements IBusinessCardListService{

    @Autowired
    private BusinessCardListMapper businessCardListMapper;

    @Override
    public Map<String, Object> Statistic(String start, String end) {
        System.out.println(start);
        List<LineChart> line = businessCardListMapper.groupByMonth(start,end);
        List<NightingaleChart> pie = businessCardListMapper.groupByType();
        Map<String,Object> chartsData = new HashMap<>();
        chartsData.put("line",line);
        chartsData.put("pie",pie);
        return chartsData;
    }
}




