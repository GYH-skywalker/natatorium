package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 期限卡种类
 * @TableName time_card
 */
@TableName(value ="time_card")
@Data
public class TimeCard implements Serializable {
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
     * 表示套餐可使用次数,一般指次卡,大于零表示具体次数,小于零时表示不限次数一般指时限卡
     */
    private Integer packageCounts;

    /**
     * 
     */
    private Double packagePrice;

    /**
     * 套餐起始时间
     */
    private String startTime;

    /**
     * 卡片类型
     */
    private String cardType;

    /**
     * 套餐结束时间
     */
    private String endTime;

    /**
     * 
     */
    private String remarks;

    /**
     * 套餐状态1,表示启用0.表示停用
     */
    private Integer enable;

    /**
     * 是否上线,1表示上线,0表示下线
     */
    private Integer isOnline;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}