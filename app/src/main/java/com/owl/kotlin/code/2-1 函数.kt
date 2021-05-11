package com.owl.kotlin.code

/**
 * Java 中方法是不能脱离类而单独存在的，但 kotlin 函数是可以单独存在的
 *   fun 函数名(参数名:参数类型):返回值类型
 * 语句: 语句是包围着代码，它没有值
 * 表达式: 表达式是有自己的值，并且可作为另一个表达式的一部分
 *
 * 函数体分为代码块函数体和表达式函数体
 * fun max(a: Int, b: Int) = if (a > b) a else b
 *    表达式函数体可省略返回值、大括号和 return，通过 = 号连接, 函数体只有一行时可使用
 * fun max(a: Int, b: Int):Int {
 *    if(a > b) {
 *       return a
 *     } else {
 *     return b
 *    }
 * }
 * 代码块函数体必须显式写出 return 和返回值类型
 *
 * 函数和方法的区别
 * 1.方法可以认为是一种特殊的函数类型
 * 2.形式上看，有 receiver(class 对象)的函数即为方法; 不依赖类，直接创建的方法即为函数
 * 3.非顶层函数引用(也就是在某个类中的方法)需要带上 receiver,属性同理
 * */
fun main(args: Array<String>) {
    yy(g3)
    println(args.contentToString())
    /*
      具名参数
        为了解决多个参数时，很难区分每个参数的含义
        如果指明了其总中一个参数的 key，在它之后的其它参数都要标明名称(之前的不需要定义)
        因为指明了 key，参数顺序可以与实际顺序不一致
        只能用于 kotlin 函数，Java 无法使用
    */

    defaultParam("2", age = 25, name = "jack")
    vararg(*array)
}

//函数的引用(非常重要，函数引用可以作为参数传递使用)
// () -> Unit
fun foo() {}

val g1: () -> Unit = ::foo


//(Int) -> String
val g2: (Int) -> String = ::foo

fun foo(p: Int): String {
    return ""

}

val g3: (Foo, String, Int) -> Any = Foo::foo

class Foo {
    fun foo(p: String, s: Int): String {
        return "$p + $s"
    }
//三种表示这个方法的形式
//val s1: Foo.(String, Int) -> String = Foo::foo
//val s2: (Foo, String, Int) -> Any = Foo::foo
//val s3 = Function3<Foo, T, T, R> = Foo::foo
}

//此时是用对象 :: foo 方法
val foo = Foo()
val g4: (String, Int) -> Any = foo::foo

fun yy(p: (Foo, String, Int) -> Any) {
    val s = p(Foo(), "2", 2)
    println(s)
}

//变长参数，通常是最后一个参数，如果要将一个数组传递给可变参数，使用 * 操作符
val array = intArrayOf(1, 2, 3)

fun vararg(vararg args: Int) {
    println(args.contentToString())
}

//多返回值，其实就是对象包装了若干个值，返回时返回这个对象(Pair，Tripe 等)

//默认参数, 解决 Java 重载函数的问题, @JvmOverloads 可以为 kotlin 多参数方法生成重载函数，提供给 Java 使用
fun defaultParam(width: String, age: Int = 24, name: String, height: Double = 173.1) {
}




