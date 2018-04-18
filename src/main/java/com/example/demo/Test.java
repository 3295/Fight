package com.example.demo;

import com.jc.tools.CalendarUtil;

import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        System.out.println(new Date());
        CalendarUtil.compareMonth(new Date(),new Date());

        Out.Inner inner=new Out().new Inner();
        Out.StaticInner inner1=new Out.StaticInner();
        String tt="sc_cd_wh";
        String[] t=tt.split("_");
        for (String s:t) {
            System.out.println(s);
        }

    }




}
