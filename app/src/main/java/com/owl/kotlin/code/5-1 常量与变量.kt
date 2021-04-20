package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/19 16:48
 */

/*
*
* val 在函数内(局部变量)时没有 get set，等同于常量，在方法外面时(全局变量)，可通过 get 改变其值，所以为只读的变量
* 常量引用：常量值可变，但常量引用不变
*
* val person = Person()
* person.age = 13
*  age 改变，但 person 并没有改变
*
*
*
* */
const val b = 3 //只能定义在全局范围，常量