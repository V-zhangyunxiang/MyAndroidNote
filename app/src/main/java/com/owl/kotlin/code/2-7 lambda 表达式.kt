package com.owl.kotlin.code

/**
 * 左边或者右边至少有一个表示类型
 *
 * 左边函数类型: (arg1, arg2...) -> { body }
 *
 * 右边函数类型实例(lambda): {参数：类型 -> 函数体}
 *
 * Lambda 表达式是函数调用的最后一个实参,它可以放在圆括号的外面，用大括号包裹
 * Lambda 表达式是函数的唯一实参,可以去掉空的圆括号对
 * Lambda 表达式中参数的类型可以被推导出来,那么类型声明可以被省略
 * 表达式中只有一个参数，并且这个参数的类型可以被推断出来，可用 it 代替该参数(不建议)
 *
 * 函数字面量有的也叫函数字面值，所谓字面量，就是不用变量直接用文本写出，比如 函数类型、lambda 表达式
 *
 * T.() -> R 带有接收者的 lambda 函数类型
 *
 * 闭包
 *   kotlin 中的 lambda 闭包跟 listener 接口是两种不同的类型
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

// lambda 省略形式
val f3: (Int) -> Unit = {
    println(it)
}

fun testLambda(s: (Int) -> Int): Int = s(2)

fun main() {
    val zz = testLambda { it + 1 }
    //testLambda { it: Int -> it + 1 } 上面等价于
    println(zz)
}