package com.example.natatorium.controller;

import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.Banner;
import com.example.natatorium.entity.Course;
import com.example.natatorium.service.IBannerService;
import com.example.natatorium.service.IFilesService;
import com.example.natatorium.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
* @author GuYuHang
* @description 针对表【banner】的Controller
* @createDate 2022-05-13 10:31:45
*/
@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController<Banner> {


    @Autowired
    private IBannerService iBannerService;
    @Autowired
    private IFilesService iFilesService;


    @PostMapping("addBanner")
    public Result addBanner(@RequestBody Banner banner, HttpServletRequest request){
        String token = request.getHeader("token");
        String userName = JWTUtil.getTokenInfo(token).getUserName();
        banner.setCreateBy(userName);
        banner.setCreateTime(new Timestamp(new Date().getTime()).toString());
        iBannerService.save(banner);
        return Result.success();
    }

    @DeleteMapping("delBanner/{id}")
    public Result delBanner(@PathVariable int id){
        Banner banner = iBannerService.getById(id);
        if(!Objects.isNull(banner)){
            if (iBannerService.removeById(id)) {
                iFilesService.del(banner.getBannerUuid());
                return Result.success();
            } else {
                return Result.error(Constants.CODE_500,"系统错误,删除失败,请联系管理员");
            }
        }else{
            return Result.error(Constants.CODE_400,"该课程不存在");
        }
    }
}