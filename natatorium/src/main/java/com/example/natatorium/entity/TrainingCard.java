package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 培训卡
 * @TableName training_card
 */
@TableName(value ="training_card")
@Data
public class TrainingCard implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 最终时间/套餐过期时间
     */
    private Date expireTime;

    /**
     * 
     */
    private Integer packagePrice;

    /**
     * 允许时差(delta_T:时间差/时差)
     */
    private Integer permitDeltaT;

    /**
     * 课时
     */
    private Integer period;

    /**
     * 提醒课时
     */
    private Integer warnPeriod;

    /**
     * 人数
     */
    private Integer population;

    /**
     * 
     */
    private String coach;

    /**
     * 
     */
    private String remarks;

    /**
     * 
     */
    private Integer enable;

    /**
     * 
     */
    private Integer isOnline;

    /**
     * 
     */
    private String trainingCardcol;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}