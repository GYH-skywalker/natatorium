package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务卡列表
 * @TableName business_list
 */
@TableName(value ="business_list")
@Data
public class BusinessList implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
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
     *
     * */
    private String cardType;

    /**
     * 业务卡状态,1标识正常,0标识异常
     */
    private Integer enable;


    private String userPhone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}