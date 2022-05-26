package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName business_card_list
 */
@TableName(value ="business_card_list")
@Data
public class BusinessCardList implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 套餐名称/业务名称/业务卡种类
     */
    private String packageName;

    /**
     * 
     */
    private String coachName;

    /**
     * 
     */
    private String cardNumber;

    /**
     * 
     */
    private String createTime;

    /**
     * 值对应业务总控businessControl里的业务类型
     */
    private String cardType;

    /**
     * 业务卡状态,1标识正常,0标识异常
     */
    private Integer enable;

    /**
     * 
     */
    private String cardUserName;

    /**
     * 
     */
    private Integer cardUserGender;

    /**
     * 
     */
    private Integer cardUserAge;

    /**
     * 
     */
    private String cardUserPhone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}