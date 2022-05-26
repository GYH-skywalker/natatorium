package com.example.natatorium.mapper;

import com.example.natatorium.entity.TimeCard;
import com.example.natatorium.common.base.BaseIMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author GuYuHang
* @description 针对表【time_card(期限卡种类)】的数据库操作Mapper
* @createDate 2022-04-20 14:00:12
* @Entity com.example.natatorium.entity.TimeCard
*/
public interface TimeCardMapper extends BaseIMapper<TimeCard> {

    @Update("update time_card set package_counts = package_counts - 1 where package_name = #{packageName}")
    int modifyCounts(@Param("packageName") String packageName);
}




