package com.owl.java;

import java.util.Date;

public class DateLong {

  public static void main(String[] args) {
    // long 转化为 date
    long s = System.currentTimeMillis();
    Date date = new Date(s);
    System.out.println(date);

    // date 转化为 long
    long l = date.getTime();
    System.out.println(l);
  }
}
