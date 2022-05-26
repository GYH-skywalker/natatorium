package com.example.natatorium.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void salt() {
        System.out.println(PasswordUtil.salt());
    }

    @Test
    void md5Digest() {
        PasswordUtil.md5Digest("101");
    }
}