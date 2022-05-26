package com.example.natatorium.entity.DTO;

import lombok.Data;

@Data
public class userVerify {
    private Integer id;
    private String userName;
    private String phone;
    private String salt;
    private String password;
    private Integer roleId;
    private String validateCode;
}
