package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName file_identification
 */
@TableName(value ="file_identification")
@Data
public class FileIdentification implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件MD5值
     */
    private String fileMd5;

    /**
     * 文件在磁盘中的地址
     */
    private String fileUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}