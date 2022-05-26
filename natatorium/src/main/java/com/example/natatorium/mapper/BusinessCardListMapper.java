package com.example.natatorium.mapper;

import com.example.natatorium.entity.BusinessCardList;
import com.example.natatorium.common.base.BaseIMapper;
import com.example.natatorium.entity.VO.LineChart;
import com.example.natatorium.entity.VO.NightingaleChart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author GuYuHang
* @description 针对表【business_card_list】的数据库操作Mapper
* @createDate 2022-05-08 19:55:51
* @Entity com.example.natatorium.entity.BusinessCardList
*/
public interface BusinessCardListMapper extends BaseIMapper<BusinessCardList> {


    List<LineChart> groupByMonth(@Param("start") String start, @Param("end") String end);

    List<NightingaleChart> groupByType();
}




