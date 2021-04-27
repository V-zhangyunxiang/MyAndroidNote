package com.owl.kotlin.code

/**
 * 内部类会持有外部类引用，使用 inner 修饰符
 *   内部类中 this@OutClass 去访问外部类的属性和方法
 *
 * 默认是嵌套类, 嵌套类不持有外部类的引用
 */
class OutClass {
    private var name: String = ""
    fun add() {}


    inner class InnerClass() {
        fun min() {
            add()
            this@OutClass.add()
            name
            this@OutClass.name
        }
    }

    class NestedClass {
        fun getValue(): String {
            //嵌套类不持有外部类的引用，必须通过外部类的对象访问
            val outClass = OutClass()
            outClass.add()
            return outClass.name
        }
    }
}

/**
 * 密封类
 * 被 sealed 修复的类默认是 open 的，且不能被实例化
 * 子类必须在密封类内部或者统一文件中
 */
sealed class SealedClass {

    class SonClass1 : SealedClass() {

    }

    object SonClass2 : SealedClass() {

    }

}

class SonClass3 : SealedClass() {

}

fun check(sealedClass: SealedClass): String =
        when (sealedClass) {
            is SealedClass.SonClass1 -> "1"
            is SealedClass.SonClass2 -> "2"
            is SonClass3 -> "3"
        }

fun main() {
    val nestedClass = OutClass.NestedClass()

    val outClass = OutClass()
    val innerClass = outClass.InnerClass()

    val sealedClass1 = SealedClass.SonClass1()
    val sealedClass2 = SealedClass.SonClass2
    val sealedClass3 = SonClass3()
}
