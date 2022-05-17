package com.owl.kotlin.code

/**
 * 1.类
 *   类的概念就是把数据和处理数据的代码封装成一个单一的实体
 *   Kotlin 也使用关键字 class 来声明类，类头与类体都是可选的，如果一个类没有类体，可以省略花括号
 *   kotlin 类默认是 publish 且 final 的
 *   Kotlin 区分了主构造方法(在类体外部声明)和次构造方法(在类体内部声明)，一个类可以有一个主构造函数和多个次构造函数，
 *   允许在 init 初始化代码块中添加初始化逻辑。
 *
 *   主构造函数
 *   主构造函数的参数可以是可变的(var)或只读的(val)，没有任何注解或者可见性修饰符，可以省略 constructor 关键字，
 *   主构造函数的参数可以在 init 初始化块中使用，构造函数的参数如果用 val/var 进行修饰，则相当于在类内部声明了一个同名的全局属性；
 *   如果不加 val/var 进行修饰，则构造函数只能在 init 函数块时使用
 *
 *   类的实例化
 *   创建一个类的实例不需要使用 Java 中的 new 关键字，像普通函数一样调用构造函数即可
 *   val three = Three(3, 4);
 *
 *   次构造函数
 *   类也可以声明包含前缀 constructor 的次构造函数，每个次构造函数都需要 直接/委托另一个次构造函数 调用主构造函数，用 this 关键字来进行指定即可
 *
 *   属性
 *   字段和 访问器(set/get) 的组合被称作属性，kotlin 属性声明使用 val 和 var，val 变量只有一个 getter，var 变量既有 getter 也有 setter
 *
 *   自定义访问器
 *   默认的访问器是 创建一个存储值的字段，返回字段的 get 和设置字段的 set，如果默认的访问器不满足需求，可以自定义 get 和 set
 *
 *   延迟初始化
 *   用 lateinit 修饰符来标记该属性，用于告诉编译器该属性会在稍后的时间被初始化
 *   用 lateinit 修饰的属性或变量必须为非空类型，并且不能是基础类型
 *
 * 2.类的分类
 *   抽象类
 *   声明为 abstract 的类为抽象类，abstract 修饰的方法为抽象方法，抽象类和抽象方法默认是 open 的
 *
 *   数据类
 *   数据类可以避免重复创建 Java 中的用于保存状态但又操作非常简单的 POJO 的模版代码
 *   数据类默认地为主构造函数中声明的属性生成了以下方法
 *   getter、setter（需要是 var）
 *   componentN
 *   copy
 *   toString
 *   hashCode
 *   equals
 *   数据类必须满足:
 *   主构造函数需要包含一个参数
 *   主构造函数的所有参数需要标记为 val 或 var
 *   数据类不能是抽象、开放、密封或者内部的
 *
 *   密封类
 *   Sealed 类用于对类创建的子类进行限制，用 Sealed 修饰的类的直接子类只允许被定义在 Sealed 类所在的文件中，可被继承
 *   密封类的构造函数只能是 private 的
 *
 *   枚举类
 *   enum class Day(val index: Int) {
 *    SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6)
 *   }
 *   枚举类也可以实现接口
 *
 *   匿名内部类
 *   使用对象表达式来创建匿名内部类实例
 *    view.setClickListener(object : OnClickListener {
 *     override fun onClick() {
 *      }
 *   })
 *
 *   嵌套类
 *   在类里面再定义的类默认是嵌套类，此时嵌套类不会包含对外部类的隐式引用
 *
 *   内部类
 *   用 inner 修饰符来标注被嵌套的类，称为内部类。内部类会隐式持有对外部类的引用
 *
 * 3.接口
 *   类使用 :接口名 来实现接口
 *   抽象方法与默认方法
 *   包含抽象方法和非抽象方法，一个类实现了多个接口，接口中有相同的方法，实现类必须重写该方法，重写时用 super<XXX>.fun1() 指明用哪个接口
 *
 *   抽象属性
 *   接口本身并不包含任何状态，因此只有实现这个接口的类在需要的情况下会存储这个值(自定义访问器或重新赋值)
 *
 * 4.继承
 *   kotlin 所有类的共同超类都是 Any，要继承一个类，使用 :父类主构造方法() 表示
 *
 *   方法/属性覆盖
 *   需要使用 open 显示标注可被重写的函数或属性，子类才能重写
 *   可以用一个 var 属性覆盖一个 val 属性，反之不行，可以扩大权限不能缩小
 *   可以在主构造函数中使用 override 重写属性
 *
 *   调用父类实现
 *   使用 super 关键字调用其父类的函数与属性，如果接口和父类有相同的方法，需要使用 super<XXX>.fun1() 显示指明用哪个
 *
 */
fun main() {

}

class Class(val x: Int, val y: Int) {

    init {
        println(x)
        println(y)
    }

    //调用主构造函数
    constructor(base: Int) : this(base + 1, base + 1) {
        println("constructor(base: Int)")
    }

    //委托给上面一个次构造函数
    constructor(base: Long) : this(base.toInt()) {
        println("constructor(base: Long)")
    }

}

class Two public @Inject constructor(val x: Int, val y: Int) {

}

class Three(x: Int, y: Int) {

    init {
        val x = 3
        val y = 6
    }

}

class Visitor(val x: Int, val y: Int) {
    lateinit var three: Three

    val isEquals1: Boolean
        get() {
            return x == y
        }

    val isEquals2
        get() = x == y

    var isEquals3 = false
        get() = x > y
        set(value) {
            field = !value
        }
}

data class PointClass(val x: Int, val y: Int)

sealed class View {

    fun click() {

    }

}

class Button : View() {

}

class TextView : View() {

}

interface OnChangedListener {

    fun onChanged()

}

enum class Day(val index: Int) : OnChangedListener {
    SUNDAY(0) {
        override fun onChanged() {

        }
    },
    MONDAY(1) {
        override fun onChanged() {

        }
    }
}

class Outer {

    private val bar = 1

    class Nested {
        fun foo1() = 2
        //错误，不持有外部类引用
        //fun foo2() = bar
    }
}

class Outer2 {

    private val bar = 1

    inner class Nested {
        fun foo1() = 2
        fun foo2() = bar
    }
}

open class BaseClass {
    open fun fun1() {
        println("BaseClass fun1")
    }
}

interface BaseInterface {
    //接口成员默认就是 open 的
    fun fun1() {
        println("BaseInterface fun1")
    }
}

class SubClass() : BaseClass(), BaseInterface {
    override fun fun1() {
        //调用 SubClass 的 fun1() 函数
        super<BaseClass>.fun1()
        //调用 BaseInterface 的 fun1() 函数
        super<BaseInterface>.fun1()
    }
}

