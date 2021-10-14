package com.owl.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
  /*正则表达式:针对字符串的操作
   * +:一次或者多次  *:0次或多次  ?:0次或一次   转义字符中\\代表一个反斜杠  \\d
   *
   * 1.匹配:
   *   使用的是String类中的boolean matches()方法
   * 2.切割:
   *   使用的是String类中的split()
   * 3.替换:
   *   使用的是String类中的replaceAll()
   * 4.获取:
   *
   */
  public static void main(String[] args) {
    String ss = "booook";
    String regex = "bo?k";
    boolean b = ss.matches(regex);

    String tel = "13455678890";
    String regex2 = "1[34578]\\d{9}";
    System.out.println(tel.matches(regex2));
    /*
    String qiege="qi,sl,ss,tt";
    String regex3=",";
    String arr[]=qiege.split(regex3);
    for(String str:arr)
    System.out.print(str);
    */
    String fenzu = "gsf$$$$$dfgrj*****fhyhd@@@@";
    String regex4 = "(.)\\1+"; // ()表示分组的意思,\\1表示取第一组的值
    String arr2[] = fenzu.split(regex4);
    for (String str : arr2) System.out.print(str);

    String haoma = "13145678900";
    String regex5 = "(\\d{3})\\d{4}(\\d{4})";
    String hm = haoma.replaceAll(regex5, "$1****$2");
    System.out.println(hm);

    String huoqu = "zhi y,u jj k,a llk";
    String regex6 = "[a-z]{4}";
    Pattern pa = Pattern.compile(regex6); // 加载规则
    Matcher ma = pa.matcher(huoqu); // 筛选
    while (ma.find()) { // 判断是否存在符合正则规则的内容
      System.out.println(ma.group()); // 得到符合规则的字符串
    }
  }
}
