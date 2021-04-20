package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/19 18:05
 */
/*
* 左边或者右边至少有一个表示类型
* Lambda 表达式是函数调用的最后一个实参,它可以放在括号的外面
* Lambda 表达式是函数的唯一实参,可以去掉空的圆括号对
* Lambda 表达式中参数的类型可以被推导出来,那么类型声明可以被省略
*
* */
val lambda: () -> Unit = {
    println("Hello")
}

val f1 = { p: Int ->
    println(p)
    Unit
    //表达式最后一行是返回值类型
}

val f2: (Int) -> Unit = { p ->
    println(p)
}

// lambda 省略形式，只有一个参数时，可用 it 代替该参数
val f3: (Int) -> Unit = {
    println(it)
}

fun testLambda(s: (Int) -> Int) = s(2) + 1

fun main() {
    val zz = testLambda { it + 1 }
    //testLambda { it: Int -> it + 1 } 上面等价于
    println(zz)
}