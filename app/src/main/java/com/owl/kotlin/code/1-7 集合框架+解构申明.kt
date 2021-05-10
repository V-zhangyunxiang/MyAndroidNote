package com.owl.kotlin.code

/**
 *  kotlin
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

    // list
    val stringList = ArrayList<String>()
    //读写
    //stringList[0] = "222"
    //val value = stringList[0]

    // add
    for (i in 1..4) {
        stringList.add(i.toString())
    }

    //链表遍历
    for (i in stringList) {
        println(i)
    }
    for ((index, value) in stringList.withIndex()) {
        println("index= $index value= $value")
    }
    for (i in stringList.indices) {
        println(stringList[i])
    }

    //map
    val m1 = HashMap<String, String>()
    //add
    for (i in 1..10) {
        m1["key = $i"] = "value = $i"
    }

    //集合遍历
    for ((key, value) in m1) {
        println("index= $key value= $value")
    }
    for (entry in m1.entries) {
        println("key=${entry.key}  value=${entry.value}")
    }

    //集合和数组互转
    //声明一个数组
    val array1 = arrayListOf("str1", "str2", "str3")
    //将数组转化为集合
    val list1 = array1.toList()
    //将集合转化为数组
    val array2 = list1.toTypedArray()

    /**
     * 解构申明: 把一个对象赋值给多个变量
     *
     * data 类默认为主构造函数参数声明了 component 函数，这也是解构的原理
     * 普通类想具备解构的功能，需要申明 operator fun component1() 函数，返回对应的变量
     * list，map，数组默认支持解构申明
     * 不需要某个变量时，使用 _ 下划线取代其名称
     * lambda 也可以使用解构申明
     */
    //pair
    //两种创建方式
    val pair = "a" to "b"
    val pair2 = Pair("a", "b")
    //获取对应元素
    val first = pair.first
    val second = pair.second
    //把 pair 拆开，相当于定义了一个 x,一个 y
    val (x, y) = pair

    //Triple
    val triple = Triple("x", 2, 3.1)
    val tFirst = triple.first
    val tSecond = triple.second
    val tThird = triple.third
    //把 triple 拆开，相当于定义了一个 e，一个 f，一个 g
    val (e, f, g) = triple
}