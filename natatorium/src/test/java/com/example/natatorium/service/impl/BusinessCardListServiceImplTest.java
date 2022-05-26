package com.example.natatorium.service.impl;

import com.example.natatorium.mapper.BusinessCardListMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusinessCardListServiceImplTest {

    @Autowired
    private BusinessCardListServiceImpl businessCardListService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void lineStatistic() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR,-1);
        Date startDate = c.getTime();
        String startStr = sdf.format(startDate);
        String endstr = sdf.format(new Date());
        System.out.println(startStr);
        System.out.println("------------------");
        System.out.println(endstr);
    }
}