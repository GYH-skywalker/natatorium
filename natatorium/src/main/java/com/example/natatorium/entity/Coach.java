package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 教练管理
 * @TableName coach
 */
@TableName(value ="coach")
@Data
public class Coach implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 救生员编号
     */
    private String lifeguardNumber;

    /**
     * 救生员资格证号
     */
    private String lifeguardCertificationNumber;

    /**
     * 
     */
    private String coachName;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private String phone;

    /**
     * 教龄/执教时间
     */
    private Integer seniority;

    /**
     * 教练照片
     */
    private String coachPhoto;

    /**
     * 签章图
     */
    private String signaturePic;

    /**
     * 
     */
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}