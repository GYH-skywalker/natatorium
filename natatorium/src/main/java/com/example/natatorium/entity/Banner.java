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
 * @TableName banner
 */
@TableName(value ="banner")
@Data
public class Banner implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件列表的uuid
     */
    private String banner;

    /**
     * 
     */
    private String createTime;

    /**
     * 
     */
    private String createBy;

    /**
     * 
     */
    private String remarks;

    private String bannerUuid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}