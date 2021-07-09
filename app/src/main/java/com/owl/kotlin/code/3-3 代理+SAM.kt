package com.owl.kotlin.code

/**
 * 对于函数式接口，可以通过 lambda 表达式实现 SAM 转换
 * 使用 lambda 表达式可以替代手动创建实现函数式接口的类
 */

interface IntPredicate {
    fun accept(i: Int): Boolean
}

// 创建一个类的实例
val isEven = object : IntPredicate {
    override fun accept(i: Int): Boolean {
        return i % 2 == 0
    }
}

// 通过 lambda 表达式创建一个实例
// val isEven2 = IntPredicate { it % 2 == 0 }

