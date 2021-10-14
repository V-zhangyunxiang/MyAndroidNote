package com.owl.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateFormatCalendar {

  public static void main(String[] args) {
    // 创建一个日期格式化对象
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
    // 创建需要被格式化的 date 对象
    Date d = new Date();
    // 使用 DateFormat 对象的格式化方法对日期格式化
    String time = df.format(d);
    // System.out.println(time);

    // 自定义所显示的日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd E hh:mm:ss");
    Date d2 = new Date();
    String time2 = sdf.format(d2);
    // System.out.println(time2);

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    int date = cal.get(Calendar.DAY_OF_MONTH);
    int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
    // System.out.println(year+"年"+month+"月"+date+"日"+",星期"+week);

    // System.out.println(Math.ceil(34.56));//大于指定数的最小整数
    // System.out.println(Math.floor(34.55));//小与指定数的最大整数
    // System.out.println(Math.pow(3, 3));//几的几次幂
    // System.out.println(Math.sqrt(4));//开方

    // 大于等于 0.0，小于 1.0 的随机小数--Math.random()
    System.out.println(Math.random());
    // 大于等于0，小于2的随机数
    System.out.println(Math.random() * 2);
    Random ra = new Random();
    System.out.println((ra.nextInt(10) + 1));
  }
}
