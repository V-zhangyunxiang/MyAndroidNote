package com.owl.kotlin.code

/**
 * 1.函数的表现形式
 *   -代码块函数体，
 *   fun 函数名(参数:参数类型):返回类型{
 *
 *   }
 *   -表达式函数体，表达式函数体的返回值类型可以省略，返回值类型可以自动推断
 *   fun 函数名(参数:参数类型) = 表达式
 *
 * 2.命名参数
 *   调用函数时可明确写出参数名称，但指定了一个参数名称后，之后的所有参数都要标明名称
 *
 * 3.默认参数值
 *   声明函数时指定参数的默认值，从而避免创建重载的函数
 *   使用命名参数，可以省略任何有默认值的参数，而且可以按照任意顺序传入需要的参数(不推荐)
 *
 * 4.可变参数
 *   使用 vararg 关键字声明可变参数
 *   数组作为可变参数传递时，需要解包数组，以便每个数组元素在函数中能够作为单独的参数来调用，在数组参数前加一个 *
 *   val names = arrayOf("leavesC", "叶应是叶", "叶")
 *   compute(* names)
 *
 * 5.局部函数
 *   支持在函数中嵌套函数，被嵌套的函数称为局部函数
 *   fun A(){
 *     fun B(){
 *     }
 *   }
 *
 * 6.表达式和循环
 *   表达式（Expression）有值，可以作为函数返回值。而语句（Statement）不总有值，不能作为函数返回值。
 *   if 表达式可以是代码块，最后的表达式作为该块的返回值。因此替代了 Java 的三元运算符
 *   when 表达式
 *   for 循环
 *
 * 7.返回和跳转
 *   return   默认从最直接包围它的函数或者匿名函数返回
 *   break    终止最直接包围它的循环
 *   continue 跳过当前，继续下一次最直接包围它的循环
 *
 *   kotlin 中任何表达式都可以用标签（label）来标记，标签的格式为 标识符@，例如：abc@ 、fooBar@ 都是有效的标签，
 *   默认标签与函数名同名
 *
 * 8.区间
 *   使用 0..10 操作符表示一个闭区间，也可用 0.rangeTo(10) 方法表示，默认自增长，从小到大
 *   使用 step 来定义每次循环递增或递增的长度
 *   使用 10 downTo 0 表示从大到小
 *   使用 until 函数声明开区间 0 until 4 表示 [0,4)
 *
 * 9.修饰符
 *   final 和 open
 *   Kotlin 中的类和方法默认都是 final 的，即不可继承的，使用 open 修饰符来标识 类/方法/属性 才能被继承
 *   如果重写了一个 类/接口 的成员(方法+属性)，重写了的成员同样默认是 open 的，如果不想被重写，添加 final 关键字
 *
 *   修饰符	      类成员	       顶层声明
 *   public 默认   所有地方可见   所有地方可见
 *   internal	  模块中可见	   模块中可见
 *   protected	  子类中可见
 *   private	  类中可见	   文件中可见
 *
 * 10.空安全
 *    kotlin 中将一个引用分为 可空/不可空 两种
 *
 *    在类型后加一个问号，表示当前变量可空，如 Int?，String？
 *    编译器不允许对可空变量不做 null 检查就直接调用其属性，减少了线上 Null 指针的频率
 *
 *    安全调用运算符：?.
 *    ?. 允许把一次 null 检查和一次方法调用合并为一个操作，如果变量值非空，则方法或属性会被调用，否则直接返回 null
 *
 *    Elvis 运算符：?:
 *    println(name ?: "default")，如果 name 不为空，则打印 name，如果为空，则打印 "default"
 *
 *    安全转换运算符：as?
 *    as? 用于把值转换为指定的类型，如果值不适合该类型则返回 null
 *    fun check(any: Any?) {
 *     val result = any as? String
 *     println(result ?: println("is not String"))
 *    }
 *
 *    非空断言：!!
 *    把任何值转换为非空类型，如果对象为空，则会抛出异常
 *
 *    可空类型的扩展
 *    val name: String? = null
 *    println(name.isNullOrEmpty()) //true
 *
 *    平台类型
 *    平台类型是 kotlin 对 java 所作的一种平衡性设计
 *    当在 kotlin 中引用 java 变量值时，由开发者自己来决定是否进行 null 检查
 *
 *  11.类型检查与转换
 *     类型检查
 *     Is 与 !is 操作符用于在运行时检查对象是否符合给定类型
 *
 *     智能转换
 *     当判断出某个对象为具体类型后，不需要显示转换，就可直接使用该类型的内部属性和方法
 *     value is Int -> println(${value.toLong})
 *
 *  12.扩展函数
 *     为一个类增加一种新的行为，类似于在 Java 中实现的静态工具方法，可以使用 this 关键字并直接调用其所有 public 方法
 *     声明一个静态的扩展函数，则必须将其定义在伴生对象上  classname.Companion.方法名
 *     如果扩展函数声明于 class 内部，则该扩展函数只能该类和其子类内部调，所以一般都是将扩展函数声明为全局函数
 *     扩展函数也可用于扩展属性
 *
 *     不可重写的扩展函数
 *     父类和子类定义了相同的扩展函数，使用哪个扩展函数是由变量的静态类型决定的，而不是运行时类型
 *     一个类的成员函数和扩展函数有相同的签名时，成员函数会被优先使用
 *
 *     建议将所有扩展函数放到统一的文件中管理，除非该扩展函数需要定义到 class 内部
 *
 *     扩展在可空判定的应用
 *     使用扩展函数接收可空的对象类型，所有类型调用该扩展方法，在扩展方法中判断 null
 *
 *  13.lambda 表达式
 *     kotlin 中的 lambda == 匿名函数
 *     Kotlin 中的函数作为参数传递的时候 本质上传递的是一个对象，lambda 取最后一行作为返回值
 *     参数类型包含函数类型，或者返回值类型是一个函数类型 的函数 都可以称之为是高阶函数
 *     ::函数名 表示将函数转化成对象，可以传递给其它函数或赋值给变量
 *
 *  14.标准库中的扩展函数
 *     let 和 run 两者相似，都接收一个函数类型的参数，最后一行作为函数的返回值，区别是 let 用 it 表示当前对象，run 用 this
 *     also 和 apply 两者相似，都接收一个函数类型的参数，返回接收者对象本身，区别是 also 用 it，apply 用 this
 *
 */

fun main() {
    val function = Function()
    //在指定了一个参数的名称后，之后的所有参数都需要标明名称
    function.compute(index = 120, value = "leavesC")
    //错误
    //function.compute(index = 110, "leavesC")

    println(function.maxValue) //20

    val age = 12
    age.doubleValue()

    val name = "张三"
    name.lastChar()

    Function.getName()
}

class Function {
    val name = "zhangsan"
    val age = 12

    //带有两个不同类型的参数，一个是 String 类型，一个是 Int 类型
    //返回值为 Int 类型
    fun test1(str: String, int: Int): Int {
        return str.length + int
    }

    //getNameLastChar 函数的返回值类型以及 return 关键字是可以省略的
    //返回值类型可以由编译器根据上下文进行推导
    //因此，函数可以简写为以下形式
    fun getNameLastChar() = name.get(name.length - 1)

    fun compute(index: Int, value: String) {
    }

    //使用命名参数，可以省略任何有默认值的参数，而且也可以按照任意顺序传入需要的参数
    fun compute(name: String = "leavesC", age: Int, value: Int = 100) {}

    fun compute(vararg name: String) {
        name.forEach { println(it) }
    }

    fun compute(name: String, country: String) {
        fun check(string: String) {
            if (string.isEmpty()) {
                throw IllegalArgumentException("参数错误")
            }
        }
        check(name)
        check(country)
    }

    //返回 20/10
    val maxValue = if (20 > 10) {
        println("maxValue is 20")
        20
    } else {
        println("maxValue is 10")
        10
    }

    fun printAge() {
        when (age) {
            in 4..9 -> println("in 4..9") //区间判断
            3 -> println("value is 3")    //相等性判断
            2, 6 -> println("value is 2 or 6")    //多值相等性判断，逗号分隔
            is Int -> println("is Int")   //类型判断
            else -> println("else")       //如果以上条件都不满足，则执行 else
        }

        //When 循环也可以不带参数
        when {
            age > 5 -> println("1 > 5")
            age > 1 -> println("3 > 1")
        }
    }

    fun printAge2() {
        val list = listOf(1, 4, 10, 34, 10)
        for (age in list) {
            println(age)
        }
        //通过索引来遍历List
        for (index in list.indices) {
            println("${index}对应的值是：${list[index]}")
        }
        //获取当前索引和相应的值
        for ((index, value) in list.withIndex()) {
            println("index : $index , value :$value")
        }
        //自定义循环区间
        for (index in 2..10) {
            println(index)
        }
    }

    // loop@ 标识 for 循环
    fun fun1() {
        val list = listOf(1, 4, 6, 8, 12, 23, 40)
        loop@ for (it in list) {
            if (it == 8) {
                continue
            }
            if (it == 23) {
                break@loop
            }
            println("value is $it")
        }
        println("function end")
    }

    fun fun2() {
        val list = listOf(1, 4, 6, 8, 12, 23, 40)
        list.forEach {
            if (it == 8) {
                return@fun2
            }
            println("value is $it")
        }
        println("function end")
        //value is 1
        //value is 4
        //value is 6
    }

    //使用的局部返回类似于在常规循环中使用 continue，跳过当前执行下一个
    fun fun3() {
        val list = listOf(1, 4, 6, 8, 12, 23, 40)
        list.forEach {
            if (it == 8) {
                return@forEach
            }
            println("value is $it")
        }
        println("function end")
        //value is 1
        //value is 4
        //value is 6
        //value is 12
        //value is 23
        //value is 40
        //function end
    }

    fun range() {
        if (age >= 0 && age <= 10) {
        }

        if (age in 0..10) {
        }

        if (age in 0.rangeTo(10)) {
        }

        for (index in 10 downTo 0) {
            println(index)
        }

        for (index in 1..8 step 2) {
            println(index)
        }

        for (index in 8 downTo 1 step 2) {
            println(index)
        }

        for (index in 0 until 4) {
            println(index)
        }
    }

    fun check(name: String?): Boolean {
        //error，编译器不允许不对 name 做 null 检查就直接调用其属性
        //return name.isNotEmpty()
        return if (name != null) {
            name.isNotEmpty()
        } else {
            false
        }
    }

    fun check2(name: String?) {
        println(name?.toUpperCase())
    }

    companion object {
        val defaultName = "mike"
    }

}

//String 类声明一个扩展函数 lastChar()，用于返回字符串的最后一个字符
//get 方法是 String 类的内部方法，length 是 String 类的内部成员变量，在此处可以直接调用
fun String.lastChar() = get(length - 1)

//为 Int 类声明一个扩展函数 doubleValue() ，用于返回其两倍值
//this 关键字代表了 Int 值本身
fun Int.doubleValue() = this * 2

//静态扩展函数
fun Function.Companion.getName(): String {
    return defaultName
}

//为 String 类新增一个属性 customLen
var String.customLen: Int
    get() = length
    set(value) {
        println("set")
    }

fun String?.check() {
    if (this == null) {
        println("this == null")
        return
    }
    println("this != null")
}
