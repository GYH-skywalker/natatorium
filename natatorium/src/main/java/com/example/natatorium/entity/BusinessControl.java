package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 业务总控
 * @TableName business_control
 */
@TableName(value ="business_control")
@Data
public class BusinessControl implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String businessName;

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