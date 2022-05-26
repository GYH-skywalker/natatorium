package com.example.natatorium.common;

import lombok.Data;

import java.util.Map;

@Data
public class oneParams {

    //查询一个时要传的参数
    private Map<String,Object> params;
}
