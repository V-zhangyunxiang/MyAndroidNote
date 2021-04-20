package com.owl.kotlin.code

/**
 *  Description
 *  @author zhangyunxiang
 *  Date 2021/4/19 15:15
 */
/*
*  扩展函数
*  扩展成员、接口不能保存 backing field
*
*
* */
class PoorGuy {
    var pocket: Double = 0.0
}

fun PoorGuy.times(count: Int): String {
    return ""
}


var PoorGuy.age: Double
    get() {
        return this.pocket
    }
    set(value) {
        this.pocket = value
    }

interface Guy {
    var moneyRight: Double
        get() {
            return 0.0
        }
        set(value) {

        }

    fun noMoney() {
        println("我是接口")
    }
}

fun main() {
    var poorGuy = PoorGuy()
    var time = poorGuy.times(5)
    var age = poorGuy.age
    println("time= $time age=$age")
}


