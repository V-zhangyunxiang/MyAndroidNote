package com.owl.java.sort;

public class MaoPao {
  // 冒泡排序，每轮排序两两相连的数进行比较，每轮取到最大的数,每轮少比较一个数(因为已经在最后面了)
  // Arrays.sort(a); 该方法直接内部用冒泡排序.
  public static void main(String[] args) {
    int[] a = {7, 6, 9, 11, 88, 2, 44};
    for (int i = 0; i < a.length - 1; i++) { // 一共有多少轮
      for (int j = 0; j < a.length - i - 1; j++) { // 每一轮比较的下标范围
        if (a[j + 1] < a[j]) { // 前一个大于后一个
          int temp;
          temp = a[j];
          a[j] = a[j + 1];
          a[j + 1] = temp;
        }
      }
    }
    System.out.println("排序后的数组元素如下:");
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + "\t");
    }
  }
}
