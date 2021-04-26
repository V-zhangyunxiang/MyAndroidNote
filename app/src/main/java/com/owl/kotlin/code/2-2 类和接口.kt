package com.owl.kotlin.code

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1

/**
 * 如果类中什么都没有，大括号可省略
 *
 * 构造方法有两种表现形式：1.定义 constructor 副构造器(不推荐) 2.在类名后接上 () 变成主构造器
 *  主构造器：其它所有的构造器必须都调用主构造器
 *  副构造器：副构造器可以委托给另一个副构造方法，最终委托给主构造方法
 *
 * 类名上设置主构造器，参数带 val 和 var 相当于定义了一个属性，不带就是普通构造器参数
 *    全局构造器参数: 全局可见
 *    普通构造器参数: 构造器内可见(init 快，属性初始化)
 *
 * 工厂函数：跟类名同名的函数
 *
 * kotlin 类、方法默认不可复写，需使用 open 关键字使方法可复写、类可继承
 *
 * AbsClass() 调用 AbsClass 无参构造方法，有参数时需要传递参数
 * */

fun SimpleClass() {
    print("我是工厂函数，不是构造函数")
}


open class SimpleClass(var x: Int, val y: String) : SimpleInf, SimpleInf2, AbsClass() {
    val z: String = "ss"

    fun test() {

    }

    init {

    }

    //重写接口里的属性，也可通过构造函数重写
    override var s: Int = 0
        get() = field
        set(value) {
            field = value
        }

    // override 强制书写
    override fun simpleMethod() {
        super<SimpleInf>.simpleMethod()//super<接口名>.方法 指明调用哪个接口的方法
    }

    override fun printf() {
    }

    override fun absMethod() {
    }

    //如果不想让子类继承该方法，添加 final 关键字
    override fun overrideable() {
    }

}

/**
 * 默认访问修饰符 public
 * 没有方法体，也可以提供一个默认方法实现; 如果有默认实现，实现类可以不重写该方法
 * :接口名 来实现一个接口，实现类重写的方法前必须加 override
 * 如果实现类实现多个接口发现有相同的方法，必须重写该方法
 * Java 类实现 kotlin 接口，通过 接口名.DefaultImpls.方法名(this) 调用接口方法的默认实现
 */
interface SimpleInf {
    val s: Int // property

    fun simpleMethod() = print("我是接口") // 默认的方法实现

    fun printf()
}

interface SimpleInf2 {
    fun simpleMethod() = print("我是接口")
}

/**
 * abstract 可以修饰类、方法、属性
 * 抽象类不可被实例化，可被继承，可包含抽象和非抽象方法，非抽象方法默认是 final 的
 * 抽象方法必须被重写
 * */
abstract class AbsClass {
    abstract fun absMethod()
    open fun overrideable() {}
}

class SimpleClass2() : SimpleClass(6, "") {
    override fun overrideable() {

    }

    override fun printf() {
    }
}

fun main() {
    //不需要 new，直接使用 类名() 实例化类
    val simpleClass = SimpleClass(9, "hello")
    SimpleClass()
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
