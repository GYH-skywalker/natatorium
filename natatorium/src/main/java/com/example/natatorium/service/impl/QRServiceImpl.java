package com.example.natatorium.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.entity.VO.QRInfo;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRServiceImpl implements IQRService {

    @Autowired
    private ITimeCardService iTimeCardService;
    @Autowired
    private IRechargeCardService iRechargeCardService;
    @Autowired
    private ITrainingCardService iTrainingCardService;
    @Autowired
    private IOwnerCardService iOwnerCardService;



    @Override
    public boolean swipeCard(QRInfo info) throws ParseException {
        QueryWrapper qw;
        qw = new QueryWrapper();
        qw.eq("package_name",info.getPackageName());
        SimpleDateFormat sdf = new SimpleDateFormat();
        switch (info.getCardType()){
            case "次卡"://次卡
                int counts = iTimeCardService.getOne(qw).getPackageCounts();
                if(counts > 0){
                     iTimeCardService.updateCounts(info.getPackageName());
                     return true;
                }else{
                    throw new serviceException(Constants.CODE_600,"你的次数已用完!");
                }
            case "月卡"://月卡
            case "年卡"://周卡
               Date endDate = sdf.parse(iTimeCardService.getOne(qw).getEndTime());
                if (new Date().before(endDate)) {
                    return true;
                } else {
                    throw new serviceException(Constants.CODE_600,"当前套餐已过期");
                }
            case "充值卡"://充值卡
                if(iRechargeCardService.getOne(qw).getPackagePrice() > 0){
                    return true;
                }else{
                    throw new serviceException(Constants.CODE_600,"余额不足!");
                }
            case "培训卡"://培训卡
                return true;
            case "业主卡"://业主卡
                if(new Date().before(sdf.parse(iOwnerCardService.getOne(qw).getEndTime()))){
                    return true;
                }else{
                    throw new serviceException(Constants.CODE_600,"当前套餐以过期");
                }
        }
        return true;
    }

    @Override
    public Map<String, Object> getCarInfo(String cardType, String packageName) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("package_name",packageName);
        Map<String,Object> result = new HashMap<>();
        switch (cardType){
            case "次卡":
                result.put("limit",iTimeCardService.getOne(qw).getPackageCounts());
                break;
            case "月卡":
            case "年卡":
                result.put("limit",iTimeCardService.getOne(qw).getEndTime());
                break;
            case "充值卡":
                result.put("limit",iRechargeCardService.getOne(qw).getWarnAmount());
                break;
            case "培训卡":
                break;
            case "业主卡":
                result.put("limit",iOwnerCardService.getOne(qw).getEndTime());
                break;
        }
        return result;
    }


}
