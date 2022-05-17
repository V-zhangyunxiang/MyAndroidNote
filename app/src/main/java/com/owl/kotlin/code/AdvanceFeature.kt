package com.owl.kotlin.code

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 1.Object 关键字
 *   对象声明
 *   类似静态工具类，常用做单例，无构造方法，可以在类中，也可以独立为一个文件
 *   object 类名 {
 *     属性声明
 *     方法声明
 *    }
 *   Java 通过 类名.INSTANCE 来调用方法或属性
 *
 *   伴生对象
 *   可以不实例化类的情况下调用类内部的函数和属性，类似 Java 的静态方法和静态变量
 *   companion object 名称(非必须){
 *     属性声明
 *     方法声明
 *   }
 *   可以为伴生对象设置名称，默认名称为 Companion，可以用 类名.伴生名.XX 调用
 *   可以实现接口，伴生对象实现了接口，其所在的类也被当做实现了该接口，可以用作接口对象传递
 *
 *   对象表达式
 *   Object 能用来声明匿名对象，可用于替代 Java 中的匿名内部类，且对象表达式中的代码可以访问并修改其外部的非 final 型的变量
 *
 * 2.委托
 *   委托模式
 *   kotlin 原生支持委托模式，有两个对象参与处理同一个请求，接受请求的对象将请求委托给另一个对象来处理，通过关键字 by 实现委托
 *
 *   属性委托
 *   将对一个属性的访问操作委托给另外一个对象来完成
 *   val/var <属性名>: <类型> by XX类()
 *   属性的委托不必实现任何的接口，但需要提供一个 getValue() 方法与 setValue()(对于 var 属性)，对一个属性的 get 和 set 操作会被委托给 属性的委托 的这两个方法
 *
 *   延迟属性
 *   Lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托
 *   第一次调用 get() 会执行已传递给 lazy() 函数的 lambda 表达式并记录结果(最后一行的值)，后续调用 get() 只是返回记录的结果
 *
 *   可观察属性
 *   Delegates.observable() 接收两个参数: 初始值以及修改属性值时的回调函数，回调函数包含三个参数：被赋值的属性、旧值与新值
 *   Delegates.vetoable() 同 observable 参数一致，通过返回一个布尔值来决定是否进行拦截，该判断逻辑是在属性被赋新值生效之前进行
 *
 * 3.注解
 *   @Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
 *   @Retention(AnnotationRetention.RUNTIME)
 *   @Repeatable
 *   @MustBeDocumented
 *   annotation class AnnotationName() 声明注解
 *
 * 4.泛型
 *   泛型类，泛型接口和泛型方法
 *   泛型类型的命名不是必须为 T，也可以使用其他「单个大写字母」，但为了便于阅读，有一些约定成俗的命名规范
 *   使用关键字 out 来支持协变，等同于 Java 中的上界通配符 ? extends，只能读不能写
 *   使用关键字 in 来支持逆变，等同于 Java 中的下界通配符 ? super，只能写不能读(只能读出 Object 类型)
 *
 *   声明处 out in
 *   在声明类的时候，给泛型符号加上 out 关键字，表明泛型参数 T 只会用来输出，在使用的时候就不用额外加 out 了(in 同理)
 *
 *   关键字 reified 配合 inline 可以用来判断泛型类型
 *
 *
 */
fun main() {
    AdvanceFeature.Animal.fly()

    AdvanceFeature.NAME
    AdvanceFeature.testFun()

    val example = Example()
    println(example.lazyValue1) //lazyValue1 computed!  Hello
    println(example.lazyValue1) //Hello
    println(example.lazyValue2) //lazyValue2 computed! leavesC

    example.age = 24 //kProperty.name: age , oldValue: -100 , newValue: 24
    example.age = 27 //kProperty.name: age , oldValue: 24 , newValue: 27

    example.age2 = 24  //kProperty.name: age , oldValue: -100 , newValue: 24
    example.age2 = -10 //kProperty.name: age , oldValue: 24 , newValue: -10
    example.age2 = 30  //kProperty.name: age , oldValue: 24 , newValue: 30 (oldValue 依然是 24，说明第二次的赋值操作被否决了)

    val annotationsTest = AnnotationsTest()
    //遍历类中的方法
    for (method in annotationsTest.javaClass.methods) {
        //查找每个方法上的注解
        for (annotation in method.annotations) {
            //判断是否存在 OnClick 注解
            if (annotation is OnClick) {
                println("method name: " + method.name)  //method name: onClickButton
                println("OnClick viewId: " + annotation.viewId)  //OnClick viewId: 200300
            }
        }
    }
}

class AdvanceFeature {

    object Animal {

        fun fly() {
            println("fly")
        }
    }

    companion object {

        const val NAME = ""

        fun testFun() {

        }
    }

    //匿名对象表达式
    private fun newThread(runnable: Runnable) = Thread(runnable)
    var count = 0
    val thread = newThread(object : Runnable {
        override fun run() {
            count++
        }
    })

}

class Delegate {
    //第一个参数表示被委托的对象、第二个参数表示被委托对象自身的描述
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return ""
    }

    //第一个参数表示被委托的对象、第二个参数表示被委托对象自身的描述，第三个参数是将要赋予的值
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    }
}

class Example {

    val lazyValue1: String by lazy {
        println("lazyValue1 computed!")
        "Hello"
    }

    //多线程情况下可以加锁来保证线程安全
    val lazyValue2: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        println("lazyValue2 computed!")
        computeLazyValue()
    }

    private fun computeLazyValue() = "leavesC"

    var age: Int by Delegates.observable(-100) { kProperty: KProperty<*>, oldValue: Int, newValue: Int ->
        println("kProperty.name: ${kProperty.name} , oldValue: $oldValue , newValue: $newValue")
    }

    var age2: Int by Delegates.vetoable(-100) { kProperty: KProperty<*>, oldValue: Int, newValue: Int ->
        println("kProperty.name: ${kProperty.name} , oldValue: $oldValue , newValue: $newValue")
        age2 <= 0 //返回true 则表示拦截该赋值操作
    }
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnClick(val viewId: Long)

class AnnotationsTest {

    @OnClick(200300)
    fun onClickButton() {
        println("Clicked")
    }

}

inline fun <reified T> printIfTypeMatch(item: Any) {
    if (item is T) {  // 这里就不会在提示错误了
        println(item)
    }
}
