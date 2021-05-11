package com.owl.kotlin.code

/**
 * kotlin 自带常用运算符
 * ==  == equals
 * +  == plus
 * in == contains
 * [] 与 get\set
 * > ==  compareTo
 * () 与 invoke()
 */
fun main() {
    val s = 2
    val sss = ""
    sss.toByteArray()
    2 + 3
    2 == 3
    2 > 3

    "2" rotate 2
}

/**
 * 运算符重载仅限官方指定的符号，一般以扩展函数的形式展现
 */
//自定义重载 加减乘除
operator fun Point.plus(o: Int): Int = 0

operator fun Point.minus(o: Int): Int = 0

operator fun Point.times(o: Int): Int = 0

operator fun Point.div(o: Int): Int = 0

class Point() {

}

// 中缀表达式
// 成员函数/扩展函数 + 只有一个参数 + infix 关键字组成
// 参数不能是可变参数，且不可有默认值
infix fun String.rotate(o: Int) {

}

// this rotate 3
class Rotate {
    infix fun rotate(o: Int) {

    }
}








