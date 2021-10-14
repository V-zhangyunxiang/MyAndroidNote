package com.owl.java;

public class Utils {

  public static void main(String[] args) {
    // System.out.println(Arrays.toString(a));//把数组类型转为字符串
    // 必须 3 个点
    // 可变参数   asList(, ,T...a);//简化了书写，代替了数组,必须写在形参数列表最后
    String[] b = {"aaa", "bbbb"};
    // 数组转集合
    // List<String> list=Arrays.asList(b);
    // System.out.println(list.size());//2
    // 数组转成的集合不能进行添加和删除,可以修改,因为数组的长度是固定的
    // 为什么要转集合?集合的方法多
    // list.set(1, "kk");
    // System.out.println(list);

    // 为什么长度为 1?
    // 集合只能存储引用数据类型的对象,因为数组是引用数据，所以把整个数组作为一个对象存储在集合中
    // int []c={1,2,3};
    // List list2=Arrays.asList(c);
    // System.out.println(list2.size());//1

    // Collentions 工具类 ,排序还是跟TreeMap的排序原理一样
    // Collentions.sort();//有许多重载的方法，用来自定义输出顺序
    // 集合转数组
    // 给定的数组长度大于集合的对象个数，则使用给定的数组
    // 如果小于集合对象个数,会创建一个新的数组，数组长度和集合对象个数相同
    // 最好给定数组长度和集合对象个数相同
    // 集合转数组可以防止数据被随意添加，删除，因为数组的长度是固定的

    // 静态导入  import static java.util.Arrays
  }
}
