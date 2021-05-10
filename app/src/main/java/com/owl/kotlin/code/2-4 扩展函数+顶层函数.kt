package com.owl.kotlin.code

/**
 *  扩展成员
 *   类外部的函数或属性
 *   不能访问到类中 private 和 protected 成员
 *   如果扩展的方法在被使用的类中有重名，导入时可使用 as 为方法起一个别名
 *   不能被重写
 *   与成员函数重名时，成员函数被优先使用
 *   this 获取扩展对象
 *   Java
 *     在使用扩展属性的 get/set 时，需使用 类对象.属性
 *
 * 顶层声明
 *    不在类中方法和属性为顶级函数或属性; 消除了 Java 中的静态工具类，独立于类存在的函数或属性
 *    不支持 protected，private 表示文件内可见，不能访问外部类的 private 成员
 *    kotlin 中通过 import 函数路径后直接使用
 *    不同的 kt 文件中，相同的顶层函数或属性间冲突
 *    与成员函数重名时默认使用顶层函数，使用成员函数需 this.函数名称(注意)
 *    Java
 *      会将顶层函数所在的 kt 文件名.函数 来调用，属性同理
 *      在文件的开头，包名的前面使用 @JvmName 自定义类名，提供给 Java 调用,属性同理
 */
class PoorGuy {
    var pocket: Double = 0.0
}

fun PoorGuy.times(count: Int): String {
    this.pocket
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


