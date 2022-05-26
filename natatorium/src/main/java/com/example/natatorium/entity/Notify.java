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
 * @TableName notify
 */
@TableName(value ="notify")
@Data
public class Notify implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String contents;

    /**
     * 
     */
    private String createTime;

    /**
     * 
     */
    private String createBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}