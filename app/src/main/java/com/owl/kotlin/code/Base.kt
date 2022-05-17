package com.owl.kotlin.code

/**
 * 1.基本特点
 *   函数可以定义在文件的最外层，不需要把它放在类中
 *   用关键字 fun 来声明一个函数，变量名在前，类型声明在后
 *   数组就是类
 *   使用 println 代替了 System.out.println
 *   省略代码结尾的分号
 *
 * 2.Package
 *   Java   规定类要放到和包结构匹配的文件夹目录结构中，而 kotlin 允许把多个类放到同一个文件中，文件名也可以任意选择
 *   Kotlin 声明的包结构不需要与目录结构对应，但最好还是保持一致，避免冲突
 *
 * 3.变量与数据类型
 *   val——不可变引用。val 声明的变量不能在初始化之后再次赋值，对应的是 Java 中的 final 变量
 *   var——可变引用。var 变量的值可以被改变，对应的是 Java 中的非 final 变量
 *   常量和变量声明时可以不指定类型，由编译器推导。但如果没有初始化值，则必须指明变量类型
 *   对于数字的类型转换，必须显示调用转换函数，如 1.toLong
 *
 *   Byte Short Int Long Float Double Char Boolean
 *   kotlin 并不区分基本数据类型和它的包装类，都作为对象存在
 *   Any
 *     所有非空类型的根类型，相当于 Java 的 Object，编译时会被编译成 Object
 *   Unit
 *     相当于 Java 的 void，可以省略，可以用于函数没有返回值时的情况，可以作为类型参数(但 void 不行)
 *   Nothing
 *     异常类型，表示一个函数非正常结束，对应 throw
 *
 *   kotlin 中不区分基本数据类型和包装类型，编译时 kotlin 会尽可能将包装类型转换成基本类型
 *   泛型中的类型参数不会被编译成基本类型，因为泛型不能存储基本类型
 *   可空的包装类型不会被编译成基本类型，因为基本类型无法存储 null 值
 *
 *   == 比较的是数值，=== 比较的是引用
 *
 *   String 表示字符串
 *    可使用 [] 访问字符串中的单个字符(0 开始)
 *    可使用 for 循环迭代字符串
 *    可使用 + 连接字符串
 *    使用 $ 符号引用字符串的值，如 $age。${name + 2} 也可与 {} 连用表示表达式。如果需要使用 $ 本身，${'$'}100.99，输出 $100.99
 *
 *   数组
 *    kotlin 中的数组是一个类，使用 Array 表示，Array 定义了 set、get、size 等属性
 *    创建数组的方法有以下几种:
 *      - arrayOf 函数创建一个数组
 *        val array1 = arrayOf("张三", "李四", "王五")
 *        array1[0] = "leavesC"
 *        println(array1[1])
 *        println(array1.size)
 *      - arrayOfNulls 创建一个给定大小的数组，包含的元素均为 null
 *        //初始元素均为 null，大小为 10 的字符数组
 *        val array2 = arrayOfNulls<String>(10)
 *      - Array 类的构造方法，传递数组的大小和一个 lambda 表达式，调用 lambda 表达式来创建元素
 *        //创建从 “a” 到 “z” 的字符串数组
 *        val array3 = Array(26) { i -> ('a' + i).toString() }
 *
 *     声明 Array<Int> 表示是一个包含装箱类型的数组
 *     如果想表示基本类型的数组，kotlin 提供了 包装类型 + Array 的方式，IntArray、ByteArray、BooleanArray 等
 *     val intArray = IntArray(5)
 *     val doubleArray = DoubleArray(5) { Random().nextDouble() } lambda 用于初始化每个元素
 *
 *  4.集合
 *    只读集合和可变集合
 *    kotlin 把访问数据的接口和修改集合数据的接口分开了，如 listOf 和 mutableListOf，mapOf 和 mutableMapOf
 *    kotlin 与 Java 代码交互时，可变与不可变都是未知的，包含的元素 null 与非 null 也是未知的
 *
 *    只读集合的可变性
 *    只读集合不一定就是不可变的，如果一个对象存在可变和不可变两个引用，当可变引用修改了对象数据后，不可变引用相当于被动被修改了，
 *    因此只读集合并不总是线程安全的
 *
 *    集合与可空性
 *    集合的可空性分为三种
 *    可以包含 null 的集合元素
 *    集合本身可以为 null
 *    集合本身可以为 null，且可以包含 null 元素
 *
 *  5.异常
 *    kotlin 中 throw 结构是一个表达式，可以作为另一个表达式的一部分来使用
 *
 *  6.中缀调用和解构声明
 *    中缀调用
 *    中缀调用只能与只有一个参数的函数一起使用，函数使用 infix 标记
 *    如 val maps = mapOf(1 to "leavesC", 2 to "ye", 3 to "czy")
 *
 *    解构声明
 *    把一个对象拆解成多个变量
 *    解构声明调用 componentN() 函数来实现，componentN() 函数要用 operator 关键字标记
 *    数据类默认生成了 componentN() 函数，非数据类想使用解构声明，需要手动实现 componentN() 函数
 *    list，map，数组、lambda 默认支持解构申明
 *    不需要某个变量时，使用 _ 下划线取代其名称
 *
 *
 * */
fun main(args: Array<String>) {
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

    val list1: List<String> = TestMain.names
    val list3: MutableList<String> = TestMain.names
    list1.forEach { println(it) } //leavesC Ye
    list3.forEach { println(it) } //leavesC Ye
    for (index in list3.indices) {
        list3[index] = list3[index].toUpperCase()
    }
    list1.forEach { println(it) } //LEAVESC YE

    "2" rotate 3

    val (name, age) = Person2("leavesC", 24)
    println("Name: $name , age: $age")

    val (x, y)  = Point2(100, 200)
    println("x: $x , y: $y")

}

// 成员函数/扩展函数 + 只有一个参数 + infix 关键字组成
// 参数不能是可变参数，且不可有默认值
infix fun String.rotate(o: Int): String {
    return o.toString()
}

data class Person2(val name: String, val age: Int)

class Point2(val x: Int, val y: Int) {
    operator fun component1() = x
    operator fun component2() = y
}











