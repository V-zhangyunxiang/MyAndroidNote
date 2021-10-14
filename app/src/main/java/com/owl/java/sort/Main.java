package com.owl.java.sort;

// 水仙花数
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    System.out.println("输入两个数，求他们之间的水仙花数");
    Scanner sc = new Scanner(System.in);
    System.out.println("请输入第一个数m");
    int m = sc.nextInt();
    System.out.println("请输入第二个数n");
    int n = sc.nextInt();
    int a, b, c;
    for (int i = m; i <= n; i++) {
      // System.out.println("此时i="+i);
      // System.out.println("此时n="+n);
      a = i / 100; // 百位
      b = i / 10 % 10; // 十位
      c = i % 10; // 个位
      if (a * a * a + b * b * b + c * c * c == i) {
        // System.out.println("进入水仙花");
        list.add(i);
      } else {
        if (i == n) {
          if (list.isEmpty()) {
            System.out.println("该范围内没有水仙花数");
          } else {
            Iterator<Integer> ite = list.iterator();
            while (ite.hasNext()) {
              Integer in = ite.next();
              System.out.print("水仙花数为:" + in + "\t");
            }
            System.out.println("查询结束");
          }
        }
      }
    }
    sc.close();
  }
}
