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
 * @TableName user_role
 */
@TableName(value ="user_role")
@Data
public class UserRole implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户创建时间
     */
    private String createTime;

    /**
     * 
     */
    private Integer enable;

    /**
     * 角色描述
     */
    @TableField("name")
    private String roleName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}