//package com.example.natatorium.interceptors;
//
//import com.auth0.jwt.exceptions.AlgorithmMismatchException;
//import com.auth0.jwt.exceptions.SignatureVerificationException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.example.natatorium.common.Constants;
//import com.example.natatorium.exception.serviceException;
//import com.example.natatorium.utils.JWTUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Slf4j
//@Component
//public class JWTInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("token");
//        String platform = request.getHeader("platform");
//
//
//        if(!StringUtils.hasText(token)){
//            throw new serviceException(Constants.CODE_401,"token is empty");
//        }else{
//            try {
//                JWTUtil.verify(token,platform);
//            }catch (SignatureVerificationException e){
//                log.error("signature exception",e);
//                throw new serviceException(Constants.CODE_401,"token验证失败请重新登录");
//            }catch (AlgorithmMismatchException e){
//                log.error("algorithm not match",e);
//                throw new serviceException(Constants.CODE_401,"token验证失败请重新登录");
//            }catch (TokenExpiredException e){
//                log.error("token expired",e);
//                throw new serviceException(Constants.CODE_401,"token验证失败请重新登录");
//            }
//        }
//        return HandlerInterceptor.super.preHandle(request, response, handler);
//    }
//}
