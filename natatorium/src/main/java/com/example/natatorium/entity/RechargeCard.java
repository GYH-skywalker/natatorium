package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 充值卡
 * @TableName recharge_card
 */
@TableName(value ="recharge_card")
@Data
public class RechargeCard implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String packageName;

    /**
     * 套餐金额

     */
    private Double packagePrice;

    /**
     * 赠送金额
     */
    private Double givenAmount;

    /**
     * 提醒金额
     */
    private Double warnAmount;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private Integer enable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}