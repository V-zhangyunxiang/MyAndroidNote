package com.owl.kotlin.code

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * data 关键字声明一个数据类
 * 主构造函数需要至少有一个参数
 * 主构造函数的所有参数需要标记为 val 或 var；
 * 数据类不能是抽象、开放、密封或者内部的；
 * 数据类是可以实现接口的，如(序列化接口)，同时也是可以继承其他类的，如继承自一个密封类
 * 自动生成 equals() hasCode()函数 toString()函数
 * 可使用 copy 生成数据副本类
 */
data class User(val name: String, val age: Int) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}

/**
 * 类委托
 * 委托的定义: 2 个对象参与处理同一请求, 委托类和被委托类都需要实现同一个约束接口，委托类接收接口对象,
 *           实例化委托类和被委托类，将被委托类实例传入委托类中，被委托类调用接口方法
 *
 * val/var 属性：类型 by <表达式>
 * 属性委托：委托属性的 set/get 方法给被委托对象，被委托对象需提供 setValue 和 getValue 方法
 *
 */
class Entrust {
    var prop: String by Delegrate()

}

class Delegrate : ReadOnlyProperty<Entrust, String>, ReadWriteProperty<Entrust, String> {

    override fun getValue(thisRef: Entrust, property: KProperty<*>): String {
        return "thisRef= $thisRef 名字为 property= ${property.name}"
    }

    override fun setValue(thisRef: Entrust, property: KProperty<*>, value: String) {
        println("委托属性为: ${property.name} 委托值为: $value")
    }

}

fun main() {
    val user1 = User("jack", 22)
    //创建一个实例的副本，并且可以在创建时修改该副本实例的某些属性
    val user2 = user1.copy(name = "tom", age = 11)
    println(user1)
    println(user2)

    val entrust = Entrust()
    println(entrust.prop)
    entrust.prop = "100"

}