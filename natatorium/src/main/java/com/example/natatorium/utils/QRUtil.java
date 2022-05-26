package com.example.natatorium.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class QRUtil {

    public static String generateQR(String encryptInfo) throws IOException {
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(1);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN);
        // 设置背景色（灰色）
        config.setBackColor(Color.GRAY);

        // 生成二维码到文件，也可以到流
        ;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        QrCodeUtil.generate(encryptInfo, config,"jpg",os);
        String QRBase64 = Base64.getEncoder().encodeToString(os.toByteArray());
        System.out.println(QRBase64);
        return QRBase64;
    }
}
