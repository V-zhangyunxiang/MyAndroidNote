package com.xiaozhu.kotlin

/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2019-06-27 19:57
 */
import  com.xiaozhu.kotlin.lastChar as lc

class Person(var name: String) {
    val isNUll: Boolean
        get() {
            return name == ""
        }
}

fun a() {
    val hello = Hello()
    hello.say()
}

fun main() {
//    a()
    var p = Person("a")
    println(p.isNUll)
    "a".lc()
}


class b() {
    val a = 3
}

class Hello {
    var b = 6

    var isSquare: Int = 2
        get() {
            return field * 2
        }

    fun say() {

        println(isSquare)
    }
}