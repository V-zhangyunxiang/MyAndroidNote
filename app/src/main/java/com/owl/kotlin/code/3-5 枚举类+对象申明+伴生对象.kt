package com.owl.kotlin.code

import android.view.View

/**
 *  enum+class 来申明枚举类
 *  类属性直接作为类的参数列表
 *  常量格式与枚举类声明的格式需一致
 *  常量和方法用 ; 分割
 */
enum class KotlinEnum(val height: String, val age: Int) {
    //常量
    Jack("178", 18),
    Tom("178", 18);

    fun Height() = height
}

/**
 * 对象声明，类似静态工具类，常用做单例
 * Java 通过类名.INSTANCE 调用方法和属性
 * 不可以使用构造方法
 * 可以声明在一个类的内部
 * 也被用在匿名内部类的场景，object 用作匿名内部类对象，可实现多个匿名内部类接口，逗号分割
 *
 * object 类名 {
 * 属性声明
 * 方法声明
 * }
 * 类名.属性
 * 类名.方法
 */
object Singleton {
    val info: String = ""
    fun returnString() = info
    fun testNoName() {
        var view: View? = null
        view?.setOnClickListener(object : View.OnClickListener, View.OnLongClickListener {
            override fun onClick(v: View?) {

            }

            override fun onLongClick(v: View?): Boolean {
                return true
            }

        })
    }
}

/**
 * 伴生对象，为了弥补 static 功能，每个类只能有一个
 * 伴生对象名称省略时，默认为 Companion
 * 伴生对象可以使用扩展函数
 *
 * class 外部类名{
 * companion object 伴生对象名{
 *    //属性
 *    //方法
 * }
 * }
 * 外部类.伴生对象内部方法
 * 外部类.伴生对象名.伴生对象内部方法
 *
 */
class B {
    private val age: Int = 0;

    companion object KB : Log {
        fun testB() {
            //可以访问外部类中的 private 成员
            B().age
            println("Companion Object...")
        }

        override fun print() {
        }
    }
}

fun B.KB.toJson() {
    print("伴生的扩展方法")
}

interface Log {
    fun print()
}

fun main() {
    B.KB.testB()
    B.testB()
}



