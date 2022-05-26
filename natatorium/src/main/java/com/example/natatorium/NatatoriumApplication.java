package com.example.natatorium;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.natatorium.mapper")
public class NatatoriumApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatatoriumApplication.class, args);
    }

}
