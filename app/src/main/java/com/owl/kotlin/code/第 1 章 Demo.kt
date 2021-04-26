package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/16 19:44
 */

fun main(vararg args: String) {
    if (args.size < 3) {
        return showHelp()
    }

    val operators = HashMap<String, (Int, Int) -> Int>()
    operators["+"] = ::add
    operators["-"] = ::del
    operators["*"] = ::times
    operators["/"] = ::div

    val op = args[1]

    try {
        println("Input: ${args.joinToString(" ")}")
        println("Output: ${operators[op]?.invoke(args[0].toInt(), args[2].toInt())}")
    } catch (e: Exception) {
        showHelp()
    }

}

fun add(a: Int, b: Int): Int {
    return a + b
}

fun del(a: Int, b: Int): Int {
    return a - b
}

fun times(a: Int, b: Int): Int {
    return a * b
}

fun div(a: Int, b: Int): Int {
    return a / b
}

fun showHelp() {
    print("""
      please input sample 3 * 4 
  """.trimIndent())
}
