package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Alias("会员信息")
public class basicMemberInfo {
    @TableId(type = IdType.AUTO)
    @TableField(insertStrategy = FieldStrategy.IGNORED)
    private Integer id;
    @TableField(value = "card_number",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String cardNumber;
    @TableField(value = "card_user_name",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String userName;
    @TableField(value = "card_user_gender",updateStrategy = FieldStrategy.NOT_EMPTY)
    private Short userGender;
    @TableField(value = "card_user_age",updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer userAge;
    @TableField(value = "card_user_phone",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String userPhone;
    @TableField(value = "card_user_birthday",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String userBirthday;
    @TableField(value = "create_time",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String createTime;
    @TableField(value = "card_user_height",updateStrategy = FieldStrategy.NOT_EMPTY)
    private Short userHeight;
    @TableField(value = "card_user_weight",updateStrategy = FieldStrategy.NOT_EMPTY)
    private Short userWeight;
    @TableField(value = "arm_length",updateStrategy = FieldStrategy.NOT_EMPTY)
    private Short armLength;
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private Short enable;
}
