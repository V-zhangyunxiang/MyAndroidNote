package com.owl.kotlin.code

/**
 *  扩展成员
 *   类外部的函数或属性
 *   类名.函数名():返回值  类名.属性:类型
 *   不能访问到类中 private 和 protected 成员
 *   如果扩展的方法在被使用的类中有重名，导入时可使用 as 为方法起一个别名
 *   不能被重写
 *   与成员函数重名时，成员函数被优先使用
 *   Java 在使用扩展属性的 get/set 时，需传递类对象
 */
class PoorGuy {
    var pocket: Double = 0.0
}

fun PoorGuy.times(count: Int): String {
    return ""
}

var PoorGuy.age: Double
    get() {
        return this.pocket
    }
    set(value) {
        this.pocket = value
    }

fun main() {
    var poorGuy = PoorGuy()
    var time = poorGuy.times(5)
    var age = poorGuy.age
    println("time= $time age=$age")
}


