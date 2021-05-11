package com.owl.kotlin.code

/**
 *
 *  方法只有一句时，可使用表达式代替语句，用 = 连接
 *
 *  if-else when try catch 语句
 *
 *  任何表达式都可以用标签（label）来标记，标签的格式为标识符后跟 @ 符号
 *  return : 默认从最直接包围它的函数或者匿名函数返回
 *  break :  终止最直接包围它的循环
 *  continue : 继续下一次最直接包围它的循环
 *
 *  通常使用隐式标签更方便，与函数同名
 *
 * */
var a = 0
var c = if (a == 3) 4 else 5

fun Test() {
    anbel@ when (a) {
        0 -> c = 5
        1 -> c = 10
        else -> c = 20

    }

    c = when (a) {
        0 -> 5
        1 -> 10
        else -> 20
    }

    c = when {
        a == 5 -> 6
        a != 9 -> 2
        else -> 20
    }

    c = try {
        5
    } catch (e: Exception) {
        e.printStackTrace()
        0
    } finally {
        // 此处的值不会影响表达式的结果
    }

    val items = listOf("apple", "banana", "kiwifruit")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }

    do {
        val y = 3
    } while (y != null) // y 在此处可见

    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
            print(it)
        }
        print(" done with implicit label")
    }
}