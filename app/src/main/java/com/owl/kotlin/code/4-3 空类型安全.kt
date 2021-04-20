package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/19 15:43
 *
 */
/*
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
* */
var nullable: String? = "hello"

var length = nullable?.length

var length2 = nullable?.length ?: 0




