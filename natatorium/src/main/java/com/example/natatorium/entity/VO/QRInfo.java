package com.example.natatorium.entity.VO;

import lombok.Data;

@Data
public class QRInfo {

    /**
     * 1.次卡,2.月卡,3.年卡,4.充值卡,5,培训卡,6.业主卡
     * */
    private String cardType;
    private String cardUserPhone;
    private String cardNumber;
    private String packageName;
    private String expireTime;
}
