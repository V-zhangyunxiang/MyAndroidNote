package com.owl.kotlin.code

/**
 *
 * forEach
 *
 * 变换：
 *
 * filter
 *
 * map
 *
 * flatMap
 *
 * 聚合
 *
 * fold  给定初始值，将元素按规则聚合，结果和初始化值类型一致
 *
 * sum   元素求和
 *
 * reduce 将元素依此按规则聚合，结果与元素类型一致
 *
 * */
fun main() {
    var list = listOf(1, 2, 3, 4)
    list.filter { it % 2 == 0 }
            .map { it * 2 + 1 }
            .forEach { println("forEach:${it}") }

    list.flatMap { 0 until it }
}

