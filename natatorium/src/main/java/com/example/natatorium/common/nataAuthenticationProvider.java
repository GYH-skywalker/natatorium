package com.example.natatorium.common;

import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.DTO.userVerify;
import com.example.natatorium.entity.users;
import com.example.natatorium.service.impl.nataConsumerDetailsService;
import com.example.natatorium.service.nataUserDetailsService;
import com.example.natatorium.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class nataAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private nataUserDetailsService nataUserDetailsService;

    @Autowired
    private nataConsumerDetailsService nataConsumerDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        userDTO uDTO = (userDTO) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        if(!StringUtils.hasText(uDTO.getUserName())&&!StringUtils.hasText(uDTO.getPhone())){
            throw new BadCredentialsException("用户名不能为空");
        }else{
            LoginUser lu = new LoginUser();
            String salt = null;
            if(StringUtils.hasText(uDTO.getUserName())){
               lu = (LoginUser) nataUserDetailsService.loadUserByUsername(uDTO.getUserName());
               salt = lu.getUser().getSalt();
            }else{
                lu = (LoginUser) nataConsumerDetailsService.loadUserByUsername(uDTO.getPhone());
                salt = lu.getConsumer().getSalt();
            }
            if(!PasswordUtil.passwordVerify(password,salt,lu.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }else{
                UsernamePasswordAuthenticationToken result =
                        new UsernamePasswordAuthenticationToken(lu,uDTO.getPassword());
                result.setDetails(authentication.getDetails());
                return result;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
