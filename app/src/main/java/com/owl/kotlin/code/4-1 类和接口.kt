package com.owl.kotlin.code

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/19 10:53
 */

/*
* kotlin 类、成员、方法默认权限都是 public; 如果类中什么都没有，大括号可省略
*
* 全局 property 必须初始化
*
* 构造方法有两种表现形式：1.定义 constructor 副构造器 2.在类名后接上 () 变成主构造器
*  主构造器：其它所有的构造器必须都调用主构造器
*  副构造器：
*
* 类名上设置主构造器，参数带 val 和 var 相当于定义了一个属性，不带就是普通构造器参数
*
* kotlin 类、方法默认不可复写，需使用 open 关键字使方法可复写、类可继承
*
* kotlin 中全局变量属于 property，Java 属于 field；区别是 property = field + set + get
* */
// AbsClass() 调用 AbsClass 无参构造方法，有参数时需要传递参数
open class SimpleClass(var x: Int, val y: String) : SimpleInf, AbsClass() {
    val z: String = "ss"
    fun test() {

    }

    override var s: Int = 0
        get() = field
        set(value) {
            field = value
        }

    // override 强制书写
    override fun simpleMethod() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun absMethod() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //如果不想让子类继承该方法，添加 final 关键字
    override fun overrideable() {

    }

}

interface SimpleInf {
    val s: Int // property
    // 可以有默认的方法实现
    fun simpleMethod() = print("我是接口")
}

abstract class AbsClass {
    abstract fun absMethod()
    // open 可复写
    open fun overrideable() {}
}

class SimpleClass2() : SimpleClass(6, "") {
    override fun overrideable() {

    }
}

fun main() {
    //不需要 new，直接使用 类名() 实例化类
    val simpleClass = SimpleClass(9, "hello")
    println(simpleClass.x)
    println(simpleClass.y)
    println(simpleClass.z)

    //属性引用 - 跟函数引用相似 - 类引用同理

    //通过类名引用，set/get 需要传递 receiver
    val property1: KMutableProperty1<SimpleClass, Int> = SimpleClass::s
    property1.set(simpleClass, 1)
    println("property1= " + property1(simpleClass))

    //通过 receiver 调用,set/get 不需要传递 receiver
    val property2: KMutableProperty0<Int> = simpleClass::s
    property2.set(2)
    property2.get()
    println("property2= " + property2())
}
