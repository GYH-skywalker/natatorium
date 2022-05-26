package com.example.natatorium.interceptors;


import cn.hutool.jwt.Claims;
import com.auth0.jwt.interfaces.Claim;
import com.example.natatorium.common.Constants;
import com.example.natatorium.common.LoginUser;
import com.example.natatorium.exception.serviceException;
import com.example.natatorium.utils.JWTUtil;
import com.example.natatorium.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");
        String platform = request.getHeader("platform");
        System.out.println("filterToken:"+token);
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println(platform);
        int userId;
        try {
            userId = JWTUtil.verify(token,platform).getClaim("id").asInt();
        } catch (Exception e) {
            e.printStackTrace();
            throw new serviceException(Constants.CODE_401,"token 非法");
        }
        LoginUser lu = redisCache.getCacheObject(platform+":login:"+userId);

        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(lu,null,lu.getAuthorities());
        System.out.println(lu.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
