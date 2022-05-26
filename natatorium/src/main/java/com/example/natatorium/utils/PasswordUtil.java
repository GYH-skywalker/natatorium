package com.example.natatorium.utils;

import com.example.natatorium.common.Constants;
import com.example.natatorium.entity.users;
import com.example.natatorium.exception.serviceException;
import sun.security.krb5.internal.PAData;

import java.security.MessageDigest;
import java.util.Objects;
import java.util.Random;

public class PasswordUtil {

    //生成16进制（radix）随机盐值
    public static String salt(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        if(sb.length()<16){
            for (int i = 0;i<16-sb.length();i++){
                sb.append(0);
            }
        }
        return Long.toHexString(Long.parseLong(sb.toString()));
    }

    //密码加密
    public static String EncryptPassword(String password,String salt){
        return md5Digest(password+salt);
    }

    //验证密码
    public static Boolean passwordVerify(String password,String salt,String passwordMD5){
        return Objects.equals(md5Digest(password+salt),passwordMD5);
    }


    //获取md5摘要(digest)
    public static String md5Digest(String src){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            StringBuilder hexValue = new StringBuilder();
            byte [] md5Byte = md5.digest(src.getBytes());
            for (byte b : md5Byte) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
