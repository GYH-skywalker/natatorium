package com.example.natatorium.config;

import com.example.natatorium.common.nataAuthenticationProvider;
import com.example.natatorium.handler.AccessDeniedHandlerImpl;
import com.example.natatorium.interceptors.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private nataAuthenticationProvider nataAuthenticationProvider;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/**")
                .authenticationProvider(this.nataAuthenticationProvider)
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
//                .antMatchers("/user/login","/user/register","/consumer/login").anonymous()
//                .antMatchers("/files/**").hasAuthority("sys:files")
//                .antMatchers("/menus/**").hasAuthority("sys:menus")
//                .antMatchers("/bmi/**").hasAuthority("sys:")
//                .antMatchers("/bc/**").hasAuthority("sys:")
//                .antMatchers("/bl/**").hasAuthority("sys:")
//                .antMatchers("/cb/**").hasAuthority("sys:")
//                .antMatchers("/consumer/**").hasAuthority("sys:")
//                .antMatchers("/ci/**").hasAuthority("sys:")
//                .antMatchers("/course/**").hasAuthority("sys:")
//                .antMatchers("/fileIdentity/**").hasAuthority("sys:")
//                .antMatchers("/oc/**").hasAuthority("sys:")
//                .antMatchers("/QR/**").hasAuthority("sys:")
//                .antMatchers("/rcc/**").hasAuthority("sys:")
//                .antMatchers("/role/**").hasAuthority("sys:")
//                .antMatchers("/roleMenus/**").hasAuthority("sys:")
//                .antMatchers("/scr/**").hasAuthority("sys:")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().permitAll();

        //允许跨域
        http.cors();
        //添加过滤器

        //配置异常处理器
        http.exceptionHandling()
                //配置认证失败处理器
                .accessDeniedHandler(accessDeniedHandler);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.nataAuthenticationProvider);
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return this.nataAuthenticationProvider;
//    }

}
