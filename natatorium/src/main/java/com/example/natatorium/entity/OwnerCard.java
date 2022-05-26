package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 业主卡
 * @TableName owner_card
 */
@TableName(value ="owner_card")
@Data
public class OwnerCard implements Serializable {
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
     * 
     */
    private Double packagePrice;

    /**
     * 套餐年份

     */
    private Object packageYear;

    /**
     * 
     */
    private String startTime;

    /**
     * 
     */
    private String endTime;

    /**
     * 
     */
    private String remarks;

    /**
     * 
     */
    private Integer enable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}