@file:JvmName("zyx")

package com.xiaozhu.kotlin

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2019-05-29 11:51
 */


class apple {
    var isSquare: Int = 0
        get() {
            return field * 2
        }

    val ass: Int = 0
        get() {
            return field + 1
        }

    fun say() {
        println(isSquare)
    }

    class banana {
        val s = 3
    }

}

fun main(a: Int, b: Int) = if (a > b) a else b


fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

fun getColor(em: KotlinEnum): String =
        when (em) {
            KotlinEnum.BLUE, KotlinEnum.RED -> "blue"
            KotlinEnum.GREEN -> "green"
            else -> "red"

        }

fun getColor2(em: KotlinEnum) =
        when (setOf(em)) {
            setOf(KotlinEnum.BLUE) -> "blue"
            setOf(KotlinEnum.GREEN) -> "green"
            else -> "red"

        }

enum class KotlinEnum(val r: Int, val g: Int, val b: Int, val colorName: String) {
    RED(255, 255, 255, "红色"), GREEN(0, 255, 0, "绿色"), A(0, 0, 0, ""),
    BLUE(0, 0, 255, "蓝色");

    //定义一个方法
    fun rgb() = (r * 256 + g) * 256 + b

    fun rgb2(name: String): String {
        return name
    }
}

fun min(c: KotlinEnum, c2: KotlinEnum) {
    when {
        (c == KotlinEnum.BLUE) -> "s"

        (c == KotlinEnum.GREEN) -> "s2"
    }

}

interface Em {}

class ema(val value: Int) : Em
class emb(val left: Em, val right: Em) : Em

fun emex(e: Em): Int {
    if (e is ema) {
        //默认智能转换，不需要 as 强转
        //val n = e as ema
        return e.value
    }

    if (e is emb) {
        return emex(e.left) + emex(e.right)
    }
    return -1
}

@JvmOverloads
fun <T> joinToString(collection: Collection<T>, separator: String = "、", prefix: String = " ", postfix: String = ""): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) {
            result.append(separator)
        }
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun String.lastChar(): Char = get(this.length - 1)


val set = hashSetOf(1, 7, 53)
val list2 = arrayListOf(1, 7, 53)

val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

const val ssss = "1"

open class View {
    open fun click() = println("View clicked")
}

class Button : View() {
    override fun click() = println("Button clicked")
}

infix fun Double.to(other: Any) = Pair(this, other)

fun main() {
//    val a = apple()
//    a.say()
//    val e = ema(3)
//    println(emex(e))


//    val list = listOf(1, 2, 3)
//    println("abcdef".lastChar())
    //println(list)
    val view = Button()
    view.click()



    //print(joinToString(list, ";", "(", ")"))
    //print(joinToString(collection = list, separator = ";"))

//    for (value in list){
//        println(value)
//    }
//    var list = arrayListOf("2", "3")
//
//    for ((index, value) in list.withIndex()) {
//        println("$index = ; $value = ")
//    }
//    val he = Person("ss")
//    println(he.isNUll)
//    val kl = 1
//    val he = Hello()
//    he.isSquare = 100
//    he.say()
//
//
//    var s = "2"
//    val c: Int = 3
//    val languages = arrayListOf("Java")
//    languages.add("Kotlin")
//    println("aaa")
//    println(s)
    //println(getColor2(KotlinEnum.GREEN))
    //print(KotlinEnum.BLUE.rgb())

}


//fun main(s: Array<String>) {
//    val name = if (s.size > 0) s[0] else "kotlin"
//    println("hello,$name")
//    println("hello,\$name")
//    println("hello, ${if (s.size > 0) s[0] else "someone"}!")
////    println("hello,${s[1]}")
//}





