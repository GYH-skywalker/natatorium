package com.example.natatorium.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.DTO.basicMemberInfoDTO;
import com.example.natatorium.entity.basicMemberInfo;
import com.example.natatorium.service.IbasicMemberInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bmi")
public class basicMemberInfoController extends BaseController<basicMemberInfo> {

    @Resource
    private IbasicMemberInfoService ibasicMemberInfoService;

    @GetMapping("/findAllByPage")
    public Result findAllByPage(@RequestParam Integer currentPage,
                                @RequestParam Integer pageSize,
                                @RequestParam(required = false) String cardNum,
                                @RequestParam(required = false) String memberName){
        QueryWrapper<basicMemberInfo> qw = new QueryWrapper<>();
        if(StringUtils.hasText(cardNum)){
            qw.like("card_number",cardNum);
        }else if (StringUtils.hasText(memberName)){
            qw.like("card_user_name",memberName);
        }
        IPage<basicMemberInfo> page = new Page<>(currentPage,pageSize);
        System.out.println("authentication:"+SecurityContextHolder.getContext().getAuthentication());
        return Result.success(ibasicMemberInfoService.page(page,qw));
    }

    @PostMapping("/add")
    public Result add(@RequestBody basicMemberInfoDTO info){
        basicMemberInfo bmi = new basicMemberInfo();
        BeanUtils.copyProperties(info,bmi);
        ibasicMemberInfoService.save(bmi);
        return Result.success();
    }

    @PutMapping("/modify")
    public Result modify(@RequestBody basicMemberInfoDTO infoDTO){
        basicMemberInfo info = new basicMemberInfo();
        BeanUtils.copyProperties(infoDTO,info);
        ibasicMemberInfoService.updateById(info);
        return Result.success();
    }
}
