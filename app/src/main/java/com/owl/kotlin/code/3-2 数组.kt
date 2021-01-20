package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2020/11/24 20:28
 */

/**
 *  int[]  -    IntArray      小写
 *  Integer[] - Array<Int>    装箱
 *  person[]  - Array<Person> 自定义类型
 *
 */

//  test2(10, (i) -> { i > 5 })

// void test2(int i，Fun1<Integer, Boolean> test) {
//      if (test.call(i)) {
//          Log.e("xxx", "show")
//      } else {
//          Log.e("xxx", "hide")
//      }
// }

fun test2(i: Int, test: (Int) -> Boolean) {
    if (test.invoke(i)) {
        println("show")
    } else {
        println("hide")
    }
}

fun main() {
    val c0 = intArrayOf(1, 2, 3, 4, 5) // doubleArrayOf() 等等
    val c1 = IntArray(5) { it + 1 }   // BooleanArray 等等
    // {} 表示数组下标 index 索引值，从第一个数组元素开始，依次为每个数组元素调用函数[init]。它应返回给定索引的数组元素的值。
    c1[0] = 2               //赋值
    println(c1)             //数组对象的 hash 值
    println(c1.toString())  //数组对象的 hash 值 ，这点与 Java 不同
    println(c1.contentToString()) //真正的输出数组元素
    println(c1.size)  //数组的长度, Java 是 length
    //数组的创建
    val d = arrayOf("hello", "world")
    BooleanArray(6) { false }
    BooleanArray(6) { i -> i > 5 }

    val s = Array<Int>(6) { i -> i }
    s[0] = 3
    println("s[]= ${s.contentToString()}")

    // lambda 的调用stringList[0]
    test2(10) { j -> j > 3 }

    //包含关系
    //in / !in 表示 ** 是否在该数组中

    //数组的遍历
    val e = floatArrayOf(1f, 2f, 3f, 4f)
    //传统遍历
    for (ele in e) {
        println(ele)
    }
    //forEach 遍历,ele 默认是 it，就可以直接把 ele-> 去掉
    e.forEach { ele -> println(ele) }

    //获取当前的遍历的 index 怎么办？
    for (i in 0 until c0.size) {
        print(c0[i])
    }

    for (i in c0.indices) {
        print(c0[i])
    }
}
