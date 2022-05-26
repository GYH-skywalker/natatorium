package com.example.natatorium.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Notify;
import com.example.natatorium.service.INotifyService;
import com.example.natatorium.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

/**
* @author GuYuHang
* @description 针对表【notify】的Controller
* @createDate 2022-05-13 10:31:56
*/
@RestController
@RequestMapping("/notify")
public class NotifyController extends BaseController<Notify> {


    @Autowired
    private INotifyService iNotifyService;


    @PostMapping("/addNotice")
    public Result addOne(@RequestBody Notify notify, HttpServletRequest request){
        String token = request.getHeader("token");
        String userName = JWTUtil.getTokenInfo(token).getUserName();
        notify.setCreateBy(userName);
        notify.setCreateTime(new Timestamp(new Date().getTime()).toString());
        iNotifyService.save(notify);
        return Result.success();
    }

//    @DeleteMapping("/delNotice/{id}")
//    public Result delNotice(@PathVariable int id){
//        LambdaQueryWrapper<Notify> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Notify::getId,id);
//        iNotifyService.remove(lqw);
//        return Result.success();
//    }

    @GetMapping("/latestNotice")
    public Result getLatest(){
        Notify notify = iNotifyService.Latest();
        return Result.success(notify);
    }
}