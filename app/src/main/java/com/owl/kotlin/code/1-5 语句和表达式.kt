package com.owl.kotlin.code

/**
 *
 *  方法只有一句时，可使用表达式代替语句，用 = 连接
 *
 *  if-else when try catch 语句
 * */
var a = 0
var c = if (a == 3) 4 else 5

fun Test() {
    when (a) {
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
}