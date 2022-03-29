package com.owl.kotlin.code

/**
 *  不需要分号结尾
 *  变量在前，类型在后，冒号隔开
 *  Java 对象是主角，也就是 class
 *  kotlin 函数是主角，不依赖 class 类
 *
 *  kotlin 中的类型就是 Java 中的类型的首字母大写
 *  Byte Short Int Long Float Double Char Boolean
 *  Any: 所有非空类型的根类型，相当于 Java 的 Object，编译时会被编译成 Object
 *  Unit: 相当于 Java 的 void，可以省略，kotlin 中 Unit 是一种类型，可作为返回值返回 return Unit
 *  Nothing: 特殊类型，表示一个函数永远不会正常结束，throw 的类型
 *  kotlin 中不区分基本数据类型和包装(对象)类型，编译时 kotlin 会尽可能将对象类型转换成 Java 基本类型
 *        - 泛型类型参数不会被编译成基本类型，因为泛型不能存储基本类型
 *        - 可空的对象类型不会被编译成基本类型，因为基本类型无法存储 Null 值
 *  编译过程：.kt 文件 > kotlin 编译器 > .class 文件 > .jar 文件 > kotlin 运行时库 > apk
 *  kotlin 运行时库包含了 kotlin 一些标准类库的定义和对 java api 的扩展
 * */
fun main() {
  /**
   *  val 只读变量 = final, 指的是当前对象不可变，对象内的元素是可变的(重要),不代表是常量
   *  var 可读写变量
   */
  val a: String = "hello word"

  /**
   * kotlin 支持类型推导
   */
  val a1 = 2

  /**
   * Java 小写 l 和 大写 L 都能表示 long
   * kotlin long 类型必须带 L
   */
  //val a2 = 123456789l  //错误
  val a3 = 123456789L  //正确

  /**
   * Java 支持隐式转换
   * kotlin 数值类型转换必须显式调用转换函数
   */
  val a4 = 2
  val a6: Long = a4.toLong()
  val a7: Float = 1f    // float 后面必须带 f(同 Java)
  val a8: Double = 2.1  //必须带小数点，默认 double

  /**
   * Kotlin 使用 $ 连接字符串和变量，需要使用 $ 本身时，用'$'
   */
  val j = "t is china"
  println("value of String 'j' is:$j")
  println("value of String 'j' is:${j.length}")
  val k = "today is sunny day"
  val m = String(k.toCharArray())
  /**
   * ==  比较值
   * === 比较引用
   */
  println(k == m)
  println(k === m)
  /**
   * """ 三个引号可以拼接连续的字符串，trimIndent 去除多余空格
   */
  val n = """
               |<html>
               |<head>
               |</head>
               |<body>
                 <div>
                   <p>this is a page</p>
                   </div>
               </body>
               </html>
                """.trimIndent()
  println(n)
}












