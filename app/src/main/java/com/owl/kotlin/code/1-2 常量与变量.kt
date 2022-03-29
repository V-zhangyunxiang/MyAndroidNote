package com.owl.kotlin.code

/**
 *
 * val 在作为局部变量时没有 get set，等同于常量；全局变量时，可通过 get 改变其值，所以为只读的变量
 * 常量引用：常量值可变，但常量引用不变
 *
 * kotlin 中全局变量属于 property，Java 属于 field
 * property = field + set + get
 * 全局变量必须显式初始化，没有默认值
 *
 * val 元素可变例子
 * val person = Person()
 * person.age = 13
 *  age 改变，但 person 并没有改变
 *
 * Kotlin 为 var 变量默认生成 getter 和 setter 方法，功能与 Java 的 getter 和 setter 方法一致
 * Kotlin 为 val 变量默认生成 getter 方法，由于 val 只能赋值一次，自然就没有 setter 方法
 * 当一个变量以 is 开头时，它的 getter 方法就是变量本身，setter 方法是去掉 is 加上 set 前缀
 *
 * const 表示常量
 *
 * */
const val b = 3 //只能定义在全局范围，常量