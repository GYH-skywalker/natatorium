package com.example.natatorium.entity.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class basicMemberInfoDTO {
    private Integer id;
    private String cardNumber;
    private String userName;
    private Short userGender;
    private Integer userAge;
    private String userPhone;
    private Date userBirthday;
    private Short userHeight;
    private Short userWeight;
    private Short armLength;
}
