package com.example.natatorium.utils;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class B{
    int x=100;
    int y=200;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXYSum(){
        return x+y;
    }
}

class QRUtilTest {

    int x;
    long y = x;
    double a;
    @BeforeEach
    void setUp() {
    }

    @Test
    void generateQR() {
//        System.out.println(a);
//        B b =new B();
//        b.setX(-100);
//        b.setY(-200);
//        System.out.println(b.getXYSum());
        try {
            QRUtil.generateQR("我的二维码的是");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}