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
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名称
     */
    @TableField("course_name")
    private String courseName;

    /**
     * 教练
     */
    private String coach;

    /**
     * 开课日期
     */
    @TableField("start_date")
    private String startDate;

    /**
     * 结课日期
     */
    @TableField("end_date")
    private String endDate;

    /**
     * 上课开始时间
     */
    @TableField("class_begins")
    private String classBegins;

    /**
     * 下课时间
     */
    @TableField("class_ends")
    private String classEnds;

    /**
     * 课程封面(保存文件中的uuid)
     */
    @TableField("course_cover")
    private String courseCover;

    /**
     * 课程备注
     * */
    private String remarks;
    /**
     * 
     */
    private Integer enable;

    private String coverUuid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}