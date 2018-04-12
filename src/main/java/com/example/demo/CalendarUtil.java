package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 线程安全处理
 */
public class CalendarUtil {

    private static ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>();

    //格式化字符串 yyyy-MM-dd HH:mm:ss
    public static Date parse(String str) throws Exception {
        return parse(str,"yyyy-MM-dd HH:mm:ss");
    }
    public static Date parse(String str,String patten) throws Exception {
        SimpleDateFormat sdf = local.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(patten);
            local.set(sdf);
        }
        return sdf.parse(str);
    }


    public static String format(Date date) throws Exception {
        return format(date,"yyyy-MM-dd HH:mm:ss");
    }
    public static String format(Date date,String patten) throws Exception {
        SimpleDateFormat sdf = local.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(patten);
            local.set(sdf);
        }
        return sdf.format(date);
    }


    /**
     * 获取某一天的时间
     * @param day
     * @return
     */
    public static String getDay(int month,int day) throws Exception{
        return getDay(new Date(),month,day);
    }

    public static String getDay(Date date,int month,int day) throws Exception{
        return getDay(date,month,day,"yyyy-MM-dd HH:mm:ss");
    }

    public static String getDay(Date date,int month,int day,String patten) throws Exception{
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(date); // 设置时间
        //ca.add(Calendar.YEAR, -1); // 年份减1
        ca.add(Calendar.MONTH, month);// 月份减1
        ca.add(Calendar.DATE, day);// 日期
        Date resultDate = ca.getTime(); // 结果
        return format(resultDate,patten);
    }

    /**
     * 月份比较
     * @param d1
     * @param d2
     * @return
     */
    public static  boolean compareMonth(Date d1,Date d2){
        int dd1=getYearMonth(d1);
        int dd2=getYearMonth(d2);
        if (dd1<dd2)return true;
        return false;
    }
    static int getYearMonth(Date dt){//传入日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);//设置时间
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份
        return year*100+month;//返回年份乘以100加上月份的值，因为月份最多2位数，所以年份乘以100可以获取一个唯一的年月数值
    }

}
