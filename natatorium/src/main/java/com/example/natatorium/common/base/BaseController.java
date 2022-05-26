package com.example.natatorium.common.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.PageParams;
import com.example.natatorium.common.Result;
import com.example.natatorium.common.oneParams;
import com.example.natatorium.entity.DTO.updateParam;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.utils.MyExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 公用控制类
 */
public class BaseController<T> {

    @Autowired//@resource 会出现多个bean的情况
    private IService<T> baseIService;

    /**
     * 总分页查询
     */
    @PostMapping("/page")
    public Result getByPage(@RequestBody PageParams pageParams) {

        IPage<T> page = new Page<T>(pageParams.getCurrentPage(), pageParams.getPageSize());
        QueryWrapper<T> qw = new QueryWrapper<T>();
        if (StringUtils.hasText(pageParams.getSearchColumn()) && StringUtils.hasText(pageParams.getSearchValue())) {
            qw.like(pageParams.getSearchColumn(), "%" + pageParams.getSearchValue() + "%");
        }
        System.out.println(pageParams.getOrderMethod());
        if (StringUtils.hasText(pageParams.getOrderMethod()) && StringUtils.hasText(pageParams.getOrderField())) {

            switch (pageParams.getOrderMethod()) {
                case "asc":
                    qw.orderByAsc(pageParams.getOrderField());
                    break;
                case "desc":
                    qw.orderByDesc(pageParams.getOrderField());
                    break;
            }
        } else if (pageParams.getMultiParams() != null) {
            for (Map.Entry<String, Object> Entry : pageParams.getMultiParams().entrySet()) {
                if (Entry.getValue() != null) {
                    if ((Entry.getValue() instanceof String) && StringUtils.hasText(Entry.getValue().toString())) {
                        qw.like(Entry.getKey(), "%" + Entry.getValue() + "%");
                    }
                    if (!(Entry.getValue() instanceof String)) {
                        qw.like(Entry.getKey(), "%" + Entry.getValue() + "%");
                    }
                }
            }
        }
        if (pageParams.getTime() != null) {
            qw.between("createTime", pageParams.getTime().get("start"), pageParams.getTime().get("end"));
        }
        qw.orderByDesc("id");
        return Result.success(baseIService.page(page, qw));
    }

    @GetMapping("/getList")
    public Result getList() {
        return Result.success(baseIService.list());
    }

    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable int id) {
        return Result.success(baseIService.getById(id));
    }

    @PostMapping("/getOne")
    public Result getOne(@RequestBody oneParams params){
        QueryWrapper<T> qw = new QueryWrapper<>();
        T one = null;
        if(params.getParams() != null){
            for (Map.Entry<String,Object> Entry : params.getParams().entrySet()) {
                if ((Entry.getValue() instanceof String) && StringUtils.hasText(Entry.getValue().toString())) {
                    qw.eq(Entry.getKey(),Entry.getValue() );
                }
                if (!(Entry.getValue() instanceof String)) {
                    qw.eq(Entry.getKey(),Entry.getValue());
                }
            }
            try {
                one = baseIService.getOne(qw);
            }catch (BadSqlGrammarException e){
                e.printStackTrace();
                if(e.getSQLException().getErrorCode() == 1054){
                    throw new serviceException(Constants.CODE_400,"当前查询字段不存在");
                }else{
                    throw new serviceException(Constants.CODE_600,e.getSQLException().getLocalizedMessage());
                }
            }
            if(Objects.isNull(one)){
                throw new serviceException(Constants.CODE_400,"该参数不存在!");
            }else{
                return Result.success(one);
            }
        }else {
            throw new serviceException(Constants.CODE_400,"查询参数不能为空");
        }
    }

    @PostMapping("/addOne")
    public Result addOne(@RequestBody T one) {
        Boolean success = baseIService.save(one);
        if (success) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "添加失败");
        }
    }

    @PostMapping("/addBatch")
    public Result addBatch(@RequestBody List<T> list) {
        Boolean success = baseIService.saveBatch(list);
        if (success) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "添加失败");
        }
    }

    @DeleteMapping("/delOne/{id}")
    public Result delOne(@PathVariable Integer id) {
        Boolean success = baseIService.removeById(id);
        if (success) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "添加失败");
        }
    }

    @DeleteMapping("/delBatch")
    public Result delBatch(@RequestBody List<Integer> list) {
        baseIService.removeBatchByIds(list);
        return Result.success();
    }

    @PutMapping("/modifyOne")
    public Result modifyOne(@RequestBody T one) {
        Boolean success = baseIService.updateById(one);
        if (success) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "添加失败");
        }
    }

    @GetMapping("/getUserExcel")
    public void getUserExcel(HttpServletResponse response) throws IOException {
        List<T> list = baseIService.list();
        MyExcelUtil.exportExcel(response, list, "文件信息");
    }

//    @PostMapping("/importExcel")
//    public Result setUsers(MultipartFile file) throws IOException{
//        List<T> list = MyExcelUtil.importExcel(file,T.class);
//        baseIService.saveBatch(list);
//        return Result.success();
//    }


    //跟新当前条目状态（是否启用）
    @GetMapping("/isEnable")
    public Result updateEnable(@RequestParam int id,
                               @RequestParam short enable) {
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.set("enable", enable).eq("id", id);
        Boolean success = baseIService.update(uw);
        if (success) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "添加失败");
        }
    }

    //跟新当前条目状态（是否启用）
    @PostMapping("/updateOne")
    public Result updateOne(@RequestBody updateParam upp) {
        UpdateWrapper<T> uw = new UpdateWrapper<>();
        uw.set(upp.getColumn(), upp.getCValue()).eq("id", upp.getCValue());
        Boolean success = baseIService.update(uw);
        if (success) {
            return Result.success();
        } else {
            return Result.error(Constants.CODE_600, "更新失败");
        }
    }


    @GetMapping("/cardPackages")
    public Result getCardPackages(HttpServletRequest request){
        QueryWrapper<T> qw = new QueryWrapper<>();
        String uri = request.getRequestURI();
        String param = "package_name";
        if("/tc/cardPackages".equals(uri)){
            param = "package_name, card_type";
        }
        qw.select(param);
        if("/Trc//cardPackages".equals(uri)){
            return null;
        }
        return Result.success(baseIService.list(qw));
    }
}
