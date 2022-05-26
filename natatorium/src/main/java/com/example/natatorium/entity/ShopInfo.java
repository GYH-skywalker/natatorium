package com.example.natatorium.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 门店信息
 * @TableName shop_info
 */
@TableName(value ="shop_info")
@Data
public class ShopInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 门店负责人
     */
    private String shopPrincipal;

    /**
     * 负责人电话
     */
    private String principalPhone;

    /**
     * 门店电话
     */
    private String shopPhone;

    /**
     * 门店地址
     */
    private String address;

    /**
     * 门店小程序/公众号二维码
     */
    private String qrCode;

    private String qrUuid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}