package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 消费项目

 * @TableName consumer_item
 */
@TableName(value ="consumer_item")
@Data
public class ConsumerItem implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称

     */
    private String itemName;

    /**
     * 项目价格

     */
    private Double itemPrice;

    /**
     * 项目状态
     */
    private Integer enable;

    /**
     * 
     */
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}