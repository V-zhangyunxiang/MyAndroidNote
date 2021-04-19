package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2020/11/25 18:00
 */

// Unit = void  Any = Object
fun main(args: Array<String>): Unit {
    yy(s33)
    println(args.contentToString())
    //具名参数  如果指明了其总中一个参数的 key，在它之后的其它参数都要标明名称(之前的不需要定义)
    defaultParam("2", age = 25, name = "jack")
}

/**
 * 函数和方法的区别
 * 1.方法可以认为是一种特殊的函数类型
 * 2.形式上看，有 receiver(class 对象)的函数即为方法; 不依赖类，直接创建的方法即为函数
 *
 *  lambda 表达式如果是最后一个参数，{可以放在圆括号外}
 * */

//函数的类型
//函数的引用(非常重要，函数引用可以作为参数传递使用)
fun foo() {}

// () -> Unit
val g1: () -> Unit = ::foo


//(Int) -> String
val g2: (Int) -> String = ::foo

fun foo(p: Int): String {
    return ""

}

val s33: (Foo, String, Int) -> Any = Foo::foo

class Foo {
    fun foo(p: String, s: Int): String {
        return "$p + $s"
    }
//三种表示这个方法的形式
//val s1: Foo.(String, Int) -> String = Foo::foo
//val s2: (Foo, String, Int) -> Any = Foo::foo
//val s3 = Function3<Foo, T, T, R> = Foo::foo
}

//此时是用对象 :: foo 方法，前面就不用带上 receiver 了
val foo = Foo()
val z: (String, Int) -> Any = foo::foo

fun yy(p: (Foo, String, Int) -> Any) {
    val s = p(Foo(), "2", 2)
    //val s = p.invoke(Foo(), "2", 2)
    println(s)
}

//变长参数
fun vararg(vararg args: Int) {
    println(args.contentToString())
}

//多返回值，其实就是类似对象包装了若干个值，返回时返回这个对象

//默认参数
fun defaultParam(width: String, age: Int = 24, name: String, height: Double = 173.1) {
}




