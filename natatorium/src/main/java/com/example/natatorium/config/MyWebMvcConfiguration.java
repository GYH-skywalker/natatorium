package com.example.natatorium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
        registry.addMapping("/**")
                //开放哪些ip、端口、域名的访问权限
                .allowedOriginPatterns("*")
                //是否允许发送Cookie信息
                .allowCredentials(true)
                //开放哪些Http方法，允许跨域访问
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                //允许HTTP请求中的携带哪些Header信息
                .allowedHeaders("*")
                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                .exposedHeaders("Authorization")
                .exposedHeaders("Content-Disposition");
    }

    @Value("${files.upload.path}")
    String filePath;//文件磁盘位置
    @Value("${files.upload.location}")
    String fileLocation;//文件虚拟映射位置

    //给上传文件添加虚拟映射位置

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileLocation).addResourceLocations("file:///"+filePath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
