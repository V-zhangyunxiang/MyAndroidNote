package com.owl.java;

public class BaseChange {
  /*包装类：
   * byte
   * short
   * int
   * long
   * char
   * boolean
   * float
   * double
   * 包装类的作用:
   * 1.可以实现基本数据类型和字符串类型的转换
   * 2.进制之间的转换
   */
  public static void main(String[] args) {
    System.out.println(Integer.MIN_VALUE);
    // 基本数据转换为字符串
    String s1 = Integer.toString(3);
    System.out.println(s1);

    String s2 = String.valueOf(false);
    // 字符串类型(该字符串必须为数字，文字会报java.lang.NumberFormatException)转换为基本数据
    int age = Integer.parseInt("25");
    System.out.println(age);
    double d = Double.parseDouble("23.56");
    System.out.println(d);
    Double s = Double.valueOf("24");
    System.out.println(s);
    // 十进制转为其他进制
    String s3 = Integer.toHexString(60); // 十六进制
    System.out.println(s3);
    String s4 = Integer.toOctalString(60); // 八进制
    System.out.println(s4);
    String s5 = Integer.toBinaryString(60); // 二进制
    System.out.println(s5);
    // 其他进制转换为十进制
    int num1 = Integer.parseInt("3c", 16); // 16表示这个数是多少进制的
    System.out.println(num1);

    // 自动装箱拆箱-----一个数如果已经创建对象了，那么再次创建，使用已有的
    // 在自动装箱时对于值从–128到127之间的值，它们被装箱为Integer对象后，会存在内存中被重用，始终只存在一个对象
    // 而如果超过了从–128到127之间的值，被装箱后的Integer对象并不会被重用，即相当于每次装箱时都新建一个Integer对象；
    // 自动装箱拆箱不仅在基本数据类型中有应用，在String类中也有应用,同一个值地址也是重用的
    String str = "sl";
    // 代替下面的声明方式
    String str2 = new String("sl");
  }
}
