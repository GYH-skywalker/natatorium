package com.example.natatorium.handler;

import cn.hutool.json.JSON;
import com.example.natatorium.common.Result;
import com.example.natatorium.utils.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        Result result = new Result(HttpStatus.FORBIDDEN.value(),"您的权限不足");
        ObjectMapper json = new ObjectMapper();
        String repInfo = json.writeValueAsString(Result.error("403","ceshi"));
        //处理异常
        WebUtils.renderString(response,repInfo);
    }
}
