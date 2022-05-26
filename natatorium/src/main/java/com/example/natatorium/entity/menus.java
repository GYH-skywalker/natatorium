package com.example.natatorium.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("sys_menu")
public class menus implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    private String path;
    private String icon;
    @TableField("menu_desc")
    private String menuDesc;
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private Integer pid;
    @TableField("page_url")
    private String pageUrl;
    private String permissions;
    private String functionMarks;
    @TableField(exist = false)
    private List<menus> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
