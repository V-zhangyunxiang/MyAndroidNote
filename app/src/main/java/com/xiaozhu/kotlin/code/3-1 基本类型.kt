package com.xiaozhu.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2020/11/20 15:42
 */

/**
 *  不需要分号结尾
 *  变量在前，类型在后，冒号隔开
 *  类型推导
 *  Java 对象是主角，也就是 class
 *  kotlin 函数是主角，不依赖 class 类
 *
 */
fun main() {
    /**
     *  val 只读变量 = final, 指的是当前对象不可变，对象内的元素是可变的(重要)
     *  var 可读写变量
     */
    val a: String = "hello word"

    /**
     * 类型推导，不用制定具体类型
     */
    val a1 = 2

    /**
     * 易混淆的 long 类型标记
     * Java 小写 l 和 大写 L 都能表示 long
     */
    //val a2 = 123456789l  //错误
    val a3 = 123456789L  //正确

    /**
     * 数值类型转换
     * java 隐式转换
     *     int e = 10;
     *     long f = e;
     */
    val a4 = 2
    //val a5: Long = a4   //错误，kotlin 不允许隐式转换
    val a6: Long = a4.toLong() //正确
    val a7: Float = 1f    // float 后面必须带 f(同Java)
    val a8: Double = 2.1  //必须带小数点

    /**
     * 无符号类型，在原类型前加个 U
     * 值不能为负数，值范围较大
     */
    val g: UInt = 10u
    val f: UByte = 1u
    val o: ULong = 1UL
    /**
     * 字符串表示
     */
    val j = "t is china"
    println("value of String 'j' is:$j")
    println("value of String 'j' is:${j.length}")

    val k = "today is sunny day"
    val m = String(k.toCharArray())
    //类比 java 中 = 和 equals
    println(k === m)  // === 比较引用
    println(k == m)//== 比较值

    val n = """
               <html>
               <head>
               </head>
               <body>
                 <div>
                   <p>this is a page</p>
                   </div>
               </body>
               </html>
                """.trimIndent()
    // """ 三个引号可以书写大量连续的字符串,trimIndent去除多余空格
    println(n)
}











