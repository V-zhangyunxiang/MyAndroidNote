package com.owl.kotlin.code

/**
 *  int[]  -    IntArray      小写
 *  Integer[] - Array<Int>    装箱
 *  person[]  - Array<Person> 自定义类型
 *
 */

fun test2(i: Int, test: (Int) -> Boolean) {
    if (test(i)) {
        println("show")
    } else {
        println("hide")
    }
}

fun main() {
    //数组的创建
    val d = arrayOf("hello", "world")
    val c0 = intArrayOf(1, 2, 3, 4, 5)
    val c1 = IntArray(5) { it }  // it 表示数组下标,从第一个数组元素开始,依次为每个数组元素调用函数[init],返回给定索引的数组元素的值
    c0[0] = 2               //写
    val s = c0[0]           //读
    println(c1)             //数组对象的 hash 值
    println(c1.toString())  //数组对象的 hash 值 ，这点与 Java 不同
    println(c1.contentToString()) //真正的输出数组元素
    println(c1.size)  //数组的长度, Java 是 length

    val booleanArray = BooleanArray(2)
    println("booleanArray[]= ${booleanArray.contentToString()}")

    val booleanArray2 = booleanArrayOf(false, true)
    println("booleanArray2[]= ${booleanArray2.contentToString()}")

    // lambda 的调用stringList[0]
    test2(10) { j -> j > 3 }

    //包含关系
    //in / !in 表示 ** 是否在该数组中

    //数组的遍历
    val e = floatArrayOf(1f, 2f, 3f, 4f)
    //传统遍历
    for (ele in e) {
        print("$ele ")
    }
    //forEach 遍历,ele 默认是 it，就可以直接把 ele-> 去掉
    e.forEach { it -> println(it) }
    //获取当前的遍历的 index 怎么办？
    for (i in e.indices) {
        print(e[i])
    }
}
