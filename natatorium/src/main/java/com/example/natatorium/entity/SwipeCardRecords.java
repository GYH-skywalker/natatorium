package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 刷卡记录
 * @TableName swipe_card_records
 */
@TableName(value ="swipe_card_records")
@Data
public class SwipeCardRecords implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 卡号
     */
    private String userCardNumber;

    /**
     * 刷卡时间
     */
    private String swipeTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}