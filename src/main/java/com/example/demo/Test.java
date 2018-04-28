package com.example.demo;

import com.jc.tools.CalendarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

        NIOTest();
        System.out.println("============================================================");
        System.out.println("1 << 4="+(1 << 4));
        System.out.println("1 << 0="+(1 << 0));
        System.out.println("1 << 2="+(1 << 2));
        System.out.println("1 << 3="+(1 << 3));
        System.out.println("1|4="+(1|4));
        System.out.println("1|8="+(1|8));
        System.out.println("1|16="+(1|16));
        System.out.println("1|16="+(4|8));
        System.out.println("1|16="+(4|16));
    }


    /**
     * NIO first code
     * @throws Exception
     */
    public static void NIOTest(){

        try {
            RandomAccessFile file = new RandomAccessFile("F:/test.log", "rw");

            FileChannel channel = file.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(48);

            int res = channel.read(byteBuffer);
            while (res != -1) {
                byteBuffer.flip();//从写模式转为读
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }
                byteBuffer.clear();
                res=channel.read(byteBuffer);
            }
            file.close();
        }catch (Exception e){
            System.out.println(e.getMessage()+e.getCause());
        }

    }

}
