package com.example.natatorium.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.entity.BusinessList;
import com.example.natatorium.entity.ConsumerBusiness;
import com.example.natatorium.entity.SwipeCardRecords;
import com.example.natatorium.entity.VO.QRInfo;
import com.example.natatorium.entity.basicMemberInfo;
import com.example.natatorium.service.*;
import com.example.natatorium.utils.JWTUtil;
import com.example.natatorium.utils.QRUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/QR")
public class QRController {


    @Autowired
    private IBusinessListService iBusinessListService;

    @Autowired
    private IQRService iqrService;

    @Autowired
    private ISwipeCardRecordsService iSwipeCardRecordsService;

    @Autowired
    private IConsumerBusinessService iConsumerBusinessService;

    @GetMapping("/getQR")
    public Result getQR(HttpServletRequest request) throws IOException {
        String token = request.getHeader("token");
        String userPhone = JWTUtil.getTokenInfo(token).getPhone();
        LambdaQueryWrapper<BusinessList> bmiQW = new LambdaQueryWrapper<>();
        bmiQW.eq(BusinessList::getUserPhone,userPhone);
        ObjectMapper json = new ObjectMapper();
        QRInfo info = new QRInfo();
        BusinessList bl = iBusinessListService.getOne(bmiQW);
        BeanUtils.copyProperties(bl,info);
        //二维码过期时间2分钟后过期(120*1000)
        Date expire = new Date(new Date().getTime() + (120*1000));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        info.setExpireTime(sdf.format(expire));
        String QRInfo = json.writeValueAsString(info);
        Map<String,Object> result = iqrService.getCarInfo(bl.getCardType(),bl.getPackageName());
        System.out.println(result);
        result.put("QR",QRUtil.generateQR(QRInfo));
        return Result.success(result);
    }


    @PostMapping("/scan")
    public Result QRScan(@RequestBody QRInfo info) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expire = sdf.parse(info.getExpireTime());
        if(new Date().before(expire)){
            if (iqrService.swipeCard(info)) {
                //添加刷卡记录
                SwipeCardRecords scr = new SwipeCardRecords();
                scr.setUserCardNumber(info.getCardNumber());
                String timsStamp = new Timestamp(new Date().getTime()).toString();
                scr.setSwipeTime(timsStamp);
                iSwipeCardRecordsService.save(scr);
                //消费记录
                ConsumerBusiness cb = new ConsumerBusiness();
                cb.setCardNumber(info.getCardNumber());
                cb.setConsumeTime(timsStamp);
                if(info.getCardType() == "次卡"){
                    cb.setConsumeQuantity(1);
                }
                iConsumerBusinessService.save(cb);
                return Result.success("刷卡成功!");
            } else {
                return Result.error(Constants.CODE_600,"刷卡失败!");
            }
        }else{
            return Result.error(Constants.CODE_600,"二维码已过期,请刷新二维码");
        }

    }
}
