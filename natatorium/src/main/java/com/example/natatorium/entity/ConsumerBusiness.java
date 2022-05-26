package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 消费业务
 * @TableName consumer_business
 */
@TableName(value ="consumer_business")
@Data
public class ConsumerBusiness implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String consumeTime;

    /**
     * 指为次卡时的消费量
     */
    private Integer consumeQuantity;

    /**
     * 
     */
    private String remarks;

    /**
     * 
     */
    private String cardNumber;

    /**
     * 消费状态,1标识正常0不正常
     */
    private Integer enable;

    /**
     * 1标识显示撤单按钮,0标识不显示
     */
    private Integer retrieval;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}