package com.xiaozhu.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2020/11/25 16:16
 */

/**
 *   kotlin                     java
 *  List<T>
 *  MutableList<T>
 *
 *  Map<K,V>
 *  MutableMap<K,V>
 *
 *  set<T>
 *  MutableSet<T>
 *
 *   Mutable 可变类型，不带的标识不可变，而 Java 是将这两者合二为一的
 * */
fun main() {
    val intList = listOf(1, 2, 3)          //不可变 list，只读
    val intList2 = mutableListOf(1, 2, 3)  //可变 list

    val map = mapOf("name" to "zyx", "age" to 23)    // to = K-V
    val map2 = mutableMapOf("name" to "zyx", "age" to 23)

    //集合的创建
    val stringList = ArrayList<String>()
    for (i in 1..10) {
        stringList += "num: $i"
        stringList -= "num: $i"
    }
    //读写
    stringList[0] = "222"
    val value = stringList[0]

    val m1 = HashMap<String, Int>()
    m1["1"] = 2
    println(m1["1"])

    //pair
    //两种创建方式
    val pair = "a" to "b"
    val pair2 = Pair("a", "b")
    //获取对应元素
    val first = pair.first
    val second = pair.second
    //解构申明，把 pair 拆开，相当于定义了一个 x,一个 y
    val (x, y) = pair

    //Triple
    val triple = Triple("x", 2, 3.1)
    val tFirst = triple.first
    val tSecond = triple.second
    val tThird = triple.third
    //解构申明，把 triple 拆开，相当于定义了一个 e，一个 f，一个 g
    val (e, f, g) = triple


}