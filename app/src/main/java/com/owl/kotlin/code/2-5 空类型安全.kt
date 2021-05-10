package com.owl.kotlin.code

import java.io.File

/**
 * ?. 安全访问符，对象为 null 时，将不会访问对应的属性或方法
 * !! 非空断言运算符，将任何值转为非空类型，对象为 null 时，将会抛出异常
 * ?: Elvis 操作符，左边表达式非空就返回左侧结果，否则就返回右侧结果
 *
 * is  instanceof 判断类型是否相等
 * as? 安全的类型转换，就不用使用 is 来判断类型，当类型转换失败时会返回 null
 *
 * String!: 平台类型
 *
 * 非空参数传递 null 也会导致 NPE(重要)
 *
 * 如何处理空值调用？
 *  1. 对象?. 安全访问(有局限) 对象!!(会抛异常)
 *  2. lateinit  延迟初始化
 *     会让编译器忽略变量的初始化，不支持基本类型
 *     必须能确认变量的生命周期下使用 lateinit
 *     要时刻判定对象是否被初始化
 *     不要在复杂的逻辑中使用，会让代码很脆弱
 *  3. lazy 委托, 默认线程安全
 *     只在 View 首次被访问时执行初始化代码
 *     只能修饰 val
 *  4. 使用条件语句判断非空后再使用
 *  5. 要只对非空值执行某个操作，安全调用操作符可以与 let 一起使用
 * */
var nullable: String? = "hello"

var length = nullable?.length

var length2 = nullable?.length ?: 0

private lateinit var mText: String


private val nameView by lazy {
    println("Hello，第一次调用才会执行我")
    "西哥！"
}


fun main() {
    mText = "sss"
    val msg: String? = "something"

    msg?.let { sendMsg(msg) } // let 函数在 msg 为空时不会调用
    
    println(nameView)
    println(nameView)
    println(nameView)
    // A ?: B，A 不满足时，使用 B
    val files = File("Test").listFiles()
    println(files?.size ?: "empty")

    //TODO()：将代码标记为不完整,该函数总是抛出一个 NotImplementedError，其返回类型为 Nothing，还有一个接受原因参数的重载
    //接收原因的重载
    TODO("ssssss")
}

fun sendMsg(msg: String) {

}







