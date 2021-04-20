package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/20 15:46
 */

/*
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
* */


