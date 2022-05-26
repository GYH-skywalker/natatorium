package com.example.natatorium.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.base.BaseController;
import com.example.natatorium.entity.menus;
import com.example.natatorium.service.ImenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/menus")
@PreAuthorize("hasAuthority('sys:menus')")
public class MenuController extends BaseController<menus> {

    @Resource
    private ImenuService imenuService;


//    @PostMapping("/add")
//    public Result addMenu(@RequestBody menus menu){
//        if (!Objects.isNull(menu)){
//            imenuService.save(menu);
//        }
//        return Result.success();
//    }
//
//    @DeleteMapping("/del/{id}")
//    public Result delMenu(@PathVariable int id){
//        imenuService.removeById(id);
//        return Result.success();
//    }

    @PutMapping("/modify")
    public Result modifyMenu(@RequestBody menus menu){
        if(imenuService.getById(menu.getId()) != null){
            imenuService.updateById(menu);
            return Result.success();
        }else {
            return Result.error(Constants.CODE_400,"该目录不存在");
        }
    }

    @GetMapping("/get")
    public Result getMenus(@RequestParam Integer currentPage,
                           @RequestParam Integer pageSize){
        IPage<menus> page = new Page<>(currentPage,pageSize);
        return Result.success(imenuService.page(page));
    }
    @GetMapping("/getAll")
//    @CrossOrigin(originPatterns = "*", allowCredentials = "true", maxAge = 3600)
    public Result getAllMenus(){
        return Result.success(imenuService.findMenusByRoleId(1));
    }

    @GetMapping("/getMenuByRole/{id}")
    public Result getMenuByRole(@PathVariable Integer id){
        return Result.success(imenuService.findMenusByRoleId(id));
    }

    @GetMapping("/menusTree")
    public Result getAllMenusTree(){
        return Result.success(imenuService.findAllMenusTree());
    }

    @GetMapping("/Pmenus")
    public Result getPmenus(){
        QueryWrapper<menus> qw = new QueryWrapper<>();
//        qw.isNotNull("pid").ne("pid",0);
        qw.isNull("pid").or().eq("pid",0);
        return Result.success(imenuService.list(qw));
    }
}
