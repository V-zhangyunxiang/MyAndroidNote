package com.owl.kotlin.code

import java.io.File

/**
 *
 * 参数类型包含函数类型或者返回值类型为函数类型的函数，就是高阶函数
 *
 * forEach map flatMap 等等
 *
 * 内联函数通常与高阶函数配合，对高阶函数的一个优化，降低程序的运行时间，使用 inline 关键字
 *
 * 编译器将使用函数的定义体来替代函数调用语句，发生在编译阶段
 *
 * 一个函数为内联函数时，该函数内的 lambda 也为内联函数，可使用 noinline 改变
 *
 * 内联函数中的 lambda 可以直接 return, 被标记为 crossinline 的 lambda 表达式不允许直接 return
 *
 * 非内联函数中的 lambda 需使用 return + @label 的形式
 *
 * 在对象的上下文中执行代码块，当对一个对象调用这样的函数并提供一个 lambda 表达式时，它会形成一个临时作用域
 * 在此作用域中，可以访问该对象而无需其名称。这些函数称为作用域函数
 *
 * let、run、with
 *   返回 lambda 结果
 * apply、also
 *   返回上下文对象，因此可以包含在调用链中
 *
 * */

class Person(var name: String, var age: Int) {
    fun add() {}
    fun mius() {}
}

fun main() {
    val person = Person("Jack", 22)
    // 对一个对象实例调用多个方法
    with(person) {
        add()
        mius()
    }

    person.let(::println)

    val numbers = mutableListOf("one", "two", "three")
    val countEndsWithE = numbers.run {
        add("four")
        add("five")
        count { it.endsWith("e") }
    }
    println("There are $countEndsWithE elements that end with e.")


    val numberList = mutableListOf<Double>()
    numberList.also { println("Populating the list") }
            .apply {
                add(2.71)
                add(3.14)
                add(1.0)
            }
            .also { println("Sorting the list") }
            .sort()

    person.also({ p: Person -> p.name = "kim" })

    val person2 = person.also { p: Person ->
        p.name = "kim"
    }
    //配置对象的属性
    val person3 = person.apply({ name = "" })

    File("build.gradle").inputStream().reader().buffered()
            .use { println(it.readLine()) }

}




