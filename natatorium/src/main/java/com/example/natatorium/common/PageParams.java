package com.example.natatorium.common;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 分页查询参数
 * */
@Data
public class PageParams {

    @ApiModelProperty("当前页码")
    private Integer currentPage;

    @ApiModelProperty("每页显示的总条目数")
    private Integer pageSize;

    @ApiModelProperty("排序方法：desc -> 降序,asc -> 升序")
    private String orderMethod;

    @ApiModelProperty("排序字段")
    private String orderField;

    @ApiModelProperty("查询列")
    private String searchColumn;

    @ApiModelProperty("查询参数")
    private String searchValue;

    @ApiModelProperty("查询条件 (参数: 模糊|大于|大于等于|小于|小于等于|等于|不等于)")
    private String condition;

    @ApiModelProperty("以时间范围查询:({start:YYYY-MM-DD hh:mm:ss,end:YYYY-MM-DD hh:mm:ss)")
    private Map<String,Object> time;

    @ApiModelProperty("多参数精确查询")
    private Map<String,Object> multiParams;


    public String getCondition() {
        if (StringUtils.isBlank(condition)){
            return "=";
        }
        switch (condition){
            case "模糊":
                return "like";
            case "大于":
                return ">";
            case "大于等于":
                return ">=";
            case "小于":
                return "<";
            case "小于等于":
                return "<=";
            case "等于":
                return "=";
            case "不等于":
                return "<>";
            default:
                return "=";
        }
    }


}
