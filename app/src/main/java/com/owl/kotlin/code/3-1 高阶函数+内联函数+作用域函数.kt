package com.owl.kotlin.code

import java.io.File

/**
 *
 * 参数类型包含函数类型或者返回值类型为函数类型的函数，就是高阶函数
 *
 * 内联函数通常与高阶函数配合，对高阶函数的一个优化，降低程序的运行时间，使用 inline 关键字，编译器将使用函数的定义体来替代函数调用语句，发生在编译阶段
 *
 *    因为高阶函数会产生临时对象，所以在像循环这种场景中，就会大量创建临时对象，为了解决这个问题，使用 inline 内联到调用处
 *
 * 一个函数为内联函数时，该函数内的 lambda 也为内联函数(具有传递性)
 *
 * 内联函数中，当 lambda 需要做为返回值使用时，此时该对象不能返回，可使用 noinline 局部关闭其优化
 *
 * lambda 不允许直接 return，可使用 return + @label 指定返回的位置
 *
 * 内联函数中的 lambda 可以直接 return, 会返回到最外层的调用
 *
 * 内联函数中，为了能让 lambda 间接调用，使用 crossinline 允许其被间接调用，但 lambda 就不允许直接 return 了，因为 return 会造成未知的不确定
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
  // with(receiver: T, block: T.() -> R) : R
  val with: Unit = with(person) {
    add()
    mius()
  }

  // T.let(block: (T) -> R) : R
  person.let(::println)

  // T.run(block: T.() -> R) : R
  val numbers = mutableListOf("one", "two", "three")
  val run = numbers.run {
    add("four")
    add("five")
    count { it.endsWith("e") }
  }
  println("There are $run elements that end with e.")

  //T.apply(block: T.() -> Unit): T
  val numberList = mutableListOf<Double>()
  val apply = numberList.apply {
    add(2.71)
    add(3.14)
    add(1.0)
  }.sort()

  //T.also(block: (T) -> Unit): T
  val also = person.also { p: Person ->
    p.name = "kim"
  }

  //block: (T) -> R : R
  File("build.gradle").inputStream().reader().buffered()
    .use { println(it.readLine()) }
}




