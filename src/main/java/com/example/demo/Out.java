package com.example.demo;

import java.util.Calendar;
import java.util.Date;

public class Out {

    static {
        System.out.println("static");
    }

    Out(){
        System.out.println("Out");
    }

    class Inner{
        Inner(){
            System.out.println("Inner");
        }
    }

    static class StaticInner{
        StaticInner(){
            System.out.println("StaticInner");
        }
    }

}
