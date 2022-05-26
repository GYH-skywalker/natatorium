package com.example.natatorium.controller;

import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.BusinessCardList;
import com.example.natatorium.entity.VO.LineChart;
import com.example.natatorium.entity.VO.NightingaleChart;
import com.example.natatorium.service.IBusinessCardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author GuYuHang
* @description 针对表【business_card_list】的Controller
* @createDate 2022-05-08 19:55:51
*/
@RestController
@RequestMapping("/bcl")
public class BusinessCardListController extends BaseController<BusinessCardList> {

    @Autowired
    private IBusinessCardListService iBusinessCardListService;

    @GetMapping("/statistic")
    public Result statisticByMonth(@RequestParam(defaultValue = "") String start,
                                   @RequestParam(defaultValue = "") String end){
        Map<String,Object> result;
        System.out.println(start+"------"+end);
        if(StringUtils.hasText(start) && StringUtils.hasText(end)){
           result = iBusinessCardListService.Statistic(start,end);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR,-1);
            Date startDate = c.getTime();
            String startStr = sdf.format(startDate);
            String endStr = sdf.format(new Date());
            result = iBusinessCardListService.Statistic(startStr,endStr);
        }
        return Result.success(result);
    }
}