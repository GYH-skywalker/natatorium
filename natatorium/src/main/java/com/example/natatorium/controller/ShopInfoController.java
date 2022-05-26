package com.example.natatorium.controller;

import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.ShopInfo;
import com.example.natatorium.service.IFilesService;
import com.example.natatorium.service.IShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author GuYuHang
* @description 针对表【shop_info(门店信息)】的Controller
* @createDate 2022-05-14 19:26:05
*/
@RestController
@RequestMapping("/si")
public class ShopInfoController extends BaseController<ShopInfo> {


    @Autowired
    private IShopInfoService iShopInfoService;

    @Autowired
    private IFilesService iFilesService;

    @PostMapping("/addShopInfo")
    public Result addShopInfo(@RequestBody ShopInfo shopInfo){
        if (iShopInfoService.save(shopInfo)) {
            return Result.success();
        } else {
            iFilesService.del(shopInfo.getQrCode());
            return Result.error(Constants.CODE_500,"添加失败");
        }
    }

}