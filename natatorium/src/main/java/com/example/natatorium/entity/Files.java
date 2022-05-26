package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName files
 */
@TableName(value ="files")
@Data
public class Files implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 条目唯一标识id
     */
    private String uuid;

    /**
     * 文件原始名称（全称）
     */
    private String fullName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小（/kb）
     */
    private Double size;

    /**
     * 文件唯一标识id
     */
    private Integer identificationId;

    /**
     * 是都可用
     */
    private Integer enable;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 
     */
    private String createTime;

    /**
     * 文件相对服务器访问地址
     * */
    private String relativeUrl;

    @TableField(exist = false)
    private FileIdentification fileIdentification;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}