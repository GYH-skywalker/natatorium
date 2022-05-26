package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("sys_user")
public class users implements Serializable {
    @TableId(type = IdType.AUTO)
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private Integer id;
    @TableField(value = "user_name",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String userName;
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;
    private Integer roleId;
    private String salt;
    private String avatar;
    private String createTime;
    private String updateTime;
    private short enable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
