package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2020/11/25 11:05
 */

fun main() {
    /**
     *  .. 闭区间，包含起止值
     */
    val intRange = 1..10 //[1,10]  包含起止值
    val charRange = 'a'..'z'
    val longRange = 1L..10L

    val floatRange = 1f..2f  //[1,2]  连续的值，无限的小数
    val doubleRange = 1.0..2.0  //[1.0,2.0]  连续的值，无限的小数
    /**
     * until 开区间,左闭右开，从小到大
     */
    val intRangeEx = 1 until 10 //[1,10）  左闭右开
    val charRangeEx = 'a' until 'z'
    val longRangeEx = 1L until 10L

    /**
     * downTo 闭区间，从大到小
     */
    val intRangeDown = 10 downTo 1 //[10,1]
    val charRangeDown = 'z' downTo 'a'
    val longRangeDown = 1L downTo 10L

    val uIntRange = 1U..10U
    val uLongRange = 1UL..10UL

    /**
     *  step 步长
     */
    val intRangeDownStep = 10 downTo 1 step 2

    println(charRangeEx.joinToString()) //输出真实的内容
    println(floatRange.toString())     //输出它自己 1.0..2.0

    //包含关系、遍历同数组相同


}