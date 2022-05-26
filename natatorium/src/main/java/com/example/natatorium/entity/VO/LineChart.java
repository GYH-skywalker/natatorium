package com.example.natatorium.entity.VO;


import com.baomidou.mybatisplus.annotation.TableField;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.VoidType;
import lombok.Data;


@Data
public class LineChart {
    @TableField(exist = false)
    private String name;
    private int month;
    private int year;
    private int data;

//    LineChart(){
//        this.name = this.getYear() + "-" + this.getMonth();
//    }


    public String getName(){
        return this.getYear() + "-" + this.getMonth();
    }
}
