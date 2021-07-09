package com.owl.kotlin.code

import android.util.Log
import com.owl.android_simple.RxJava1Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@InternalCoroutinesApi
fun main() {
    flow1()
}


@InternalCoroutinesApi
fun flow1() {
    //原生创建
    //flowOf、asFlow(数组、列表)
    //flowOf("2","3","4")
    //listOf(2,4,6).asFlow()
    //1.改变数据发射的线程 flowOn - subscribeOn(RxJava)
    //2.collect 消费数据的线程是 启动协程时指定的调度器
    //3.collect 订阅时，flow 才发射
    //4.有序发射数据，所有的数据都会经过 filter、map、collect
    //5.Flow 采用和协程一样的协作取消
    //back Pressure
    kotlinx.coroutines.runBlocking {
        flow {
            for (i in 1..10) {
                emit(i)
            }
        }.flowOn(Dispatchers.IO)
                .filter { it ->
                    it % 2 == 0
                }
                .onStart { println("Starting flow") }
                .onEach { println("On each $it") }
                .map { it * 2 }
                .catch { e ->
                    println(e.message)
                    println(e.cause)
                }
                .onCompletion { cause: Throwable? ->
                    if (cause != null) {
                        println("错误时${cause.message}")
                    } else {
                        println("完成了")
                    }
                }
                .collect(object : FlowCollector<Int> {
                    override suspend fun emit(value: Int) {
                        println("collect= $value")
                    }

                })
    }

}
