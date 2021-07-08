package com.owl.kotlin.code

/**
 * Java 中的循环主要包括 while()、do{}while()、for 和 foreach
 */
fun main() {
    //打印出 1-100 之间的数字，包括 1 和 100
    for (i in 1..100) {
        print(i)
    }
    // 打印出 1-100 之间的所有奇数
    for (i in 1..100 step 2) {
        println(i)
    }
    //从 100 减小到 1，step 仍然表示步长
    for (i in 100 downTo 1 step 1) {
        print(i);
    }

    // 半开区间,左闭右开：不包含 100
    for (i in 1 until 100) {
        print(i);
    }
}