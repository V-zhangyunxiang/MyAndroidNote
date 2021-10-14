package com.owl.java.sort;

public class XuanZe {
  // 选择排序:每轮用一个数与后面的数依次比较
  public static void main(String[] args) {
    int[] a = {7, 6, 9, 11, 88, 2, 44};
    for (int i = 0; i < a.length - 1; i++) {
      for (int j = i + 1; j < a.length; j++) {
        if (a[j] < a[i]) {
          int temp;
          temp = a[i];
          a[i] = a[j];
          a[j] = temp;
        }
      }
    }
    System.out.println("排序后的数组元素如下:");
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + "\t");
    }
  }
}
