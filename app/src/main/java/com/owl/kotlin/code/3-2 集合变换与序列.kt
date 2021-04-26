package com.owl.kotlin.code

/**
 *
 * forEach
 *
 * map
 *
 * flatMap
 *
 * */
fun main() {
    var list = listOf(1, 2, 3, 4)
    list.filter { it % 2 == 0 }
            .map { it * 2 + 1 }
            .forEach { println("forEach:${it}") }

    list.flatMap { 0 until it }
}

