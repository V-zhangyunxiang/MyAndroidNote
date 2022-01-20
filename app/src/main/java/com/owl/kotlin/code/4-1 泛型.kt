package com.owl.kotlin.code

/**
 * Java 的泛型本身是不支持协变和逆变的
 *
 *  可以使用泛型通配符 ? extends 来使泛型支持协变，但是「只能读取不能修改」，这里的修改仅指对泛型集合添加元素，如果是 remove(int index) 以及 clear 当然是可以的
 *
 *  可以使用泛型通配符 ? super 来使泛型支持逆变，但是「只能修改不能读取」，这里说的不能读取是指不能按照泛型类型读取，你如果按照 Object 读出来再强转当然也是可以的
 *
 * kotlin 本身也不支持协变和逆变
 *   out 相当于 extends
 *   in 相当于 super
 *
 * 声明处型变：可以在声明类时就使用 out/in，这样实例化时就不用添加 out/in 关键字了
 *
 * * 号相当于 Java * 通配符
 * : 号限制泛型类型，多个时使用 where
 *
 * reified 关键字
 */
open class Nil<out T> {

  //泛型约束
  fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
  }

  //多个泛型约束
  fun <T> maxOf(a: T, b: T): Unit where T : Comparable<T>, T : () -> Unit {
    if (a > b) a() else b()
  }

  class Nil2<E> : Nil<E>() {

  }

  inline fun <reified T> printIfTypeMatch(item: Any) {
    if (item is T) {  // 这里就不会在提示错误了
      println(item)
    }
  }

}



