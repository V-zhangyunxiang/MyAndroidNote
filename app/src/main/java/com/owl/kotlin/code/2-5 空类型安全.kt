package com.owl.kotlin.code

/**
 * ?. 安全访问，对象为 null 时，将不会访问对应的属性或方法
 * !! 强制转换，对象为 null 时，将会抛出异常
 * ?: 类似 Java 的三目运算符，后面跟上前者为 false 时值
 *
 * is = instanceof 判断类型是否相等
 * as? = 安全的强制转换,就不用使用 is 来判断类型，当类型转换失败时会返回 null
 *
 * String!: 平台类型
 *
 * 尽量使用 val 声明不可变引用，让程序含义明确安全
 *
 * 尽可能减少函数对外部变量的访问
 *
 * 必要时创建局部变量指向外部变量，避免因它变化引起的程序错误
 *
 * 非空参数传递 null 也会导致 NPE(重要)
 *
 * * 延迟初始化
 *  ？+ null 声明变量初始化为 null
 *  lateinit
 *    会让编译器忽略变量的初始化，不支持基本类型
 *    必须能确认变量的生命周期下使用 lateinit
 *    要时刻判定对象是否被初始化
 *    不要在复杂的逻辑中使用，会让代码很脆弱
 *
 *  lazy 委托, 默认线程安全
 *    只在 View 首次被访问时执行初始化代码
 *
 * 为可空类型添加扩展函数处理 Null 值
 *
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
}

fun sendMsg(msg: String) {

}






