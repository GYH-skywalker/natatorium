package com.example.natatorium.service;

import com.example.natatorium.entity.VO.QRInfo;

import java.text.ParseException;
import java.util.Map;

public interface IQRService {
    boolean swipeCard(QRInfo info) throws ParseException;

    Map<String, Object> getCarInfo(String cardType, String packageName);
}
