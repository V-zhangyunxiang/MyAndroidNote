package com.owl.kotlin.code

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 *  launch 函数不是顶层函数，是不能直接用的
 *
 *  让复杂的并发代码，写起来变得简单且清晰，是协程的优势(回调嵌套等)
 *
 *  withContext 函数可以切换到指定的线程，并在闭包内的逻辑执行结束之后，自动把线程切回去继续执行
 *  withContext 也可以放到单独的函数里
 *
 *  suspend 是 Kotlin 协程最核心的关键字；withContext 是一个 suspend 函数，它需要在协程或者是另一个 suspend 函数中调用

 *  Dispatchers.Default    共享后台线程池里的线程
 *  Dispatchers.Main       Android 主线程
 *  Dispatchers.IO         IO 密集型的任务
 *  Dispatchers.Unconfined 不限制，使用父 Coroutine 的线程
 *  newSingleThreadContext 使用新的线程
 *
 *  协程在执行某一个 suspend 函数时，这个协程会被挂起，这个协程或从正在执行它的线程上脱离,此时
 *    线程：要么无事可做，被回收；要么继续执行别的后台任务
 *    协程：在指定线程执行 suspend 函数代码，完成后自动切回原来的线程
 *  suspend 函数只是一个提醒，限制这个函数必须在协程里被调用，光它并不能实现挂起操作
 *  当你需要做耗时操作时，需要定义 suspend 函数，耗时包括: IO、网络请求、CPU 计算、延迟等待
 *  挂起操作的实现：withContext delay(延迟等待)
 *
 *  什么是协程？
 *   协程就是切线程
 *  什么是挂起？
 *   挂起就是可以自动切回来的切线程
 *  什么是非阻塞式挂起？
 *   挂起的非阻塞式指的是它能用看起来阻塞的代码写出非阻塞的操作
 *
 */
fun main() {
    //globalScope()
    //runBlocking()
    //coroutineScope()
    supervisorScope()
}

/**
 *    方法一 使用 runBlocking 顶层函数，线程阻塞，但其内部协程是非阻塞的
 *    runBlocking 只会等待相同作用域的协程完成才会退出，而不会等待 GlobalScope 等其它作用域启动的协程完成
 *    runBlocking {
 *     getImage(imageId)
 *    }
 */
fun runBlocking() {
    println("start")
    runBlocking {
        launch {
            repeat(3) {
                //delay(100)
                println("launchA - $it")
            }
        }
        launch {
            repeat(3) {
                //delay(100)
                println("launchB - $it")
            }
        }
        GlobalScope.launch {
            repeat(3) {
                //delay(110)
                println("GlobalScope - $it")
            }
        }
    }
    println("end")
}

/**
 *    方法二 使用 GlobalScope 全局作用域单例对象，非线程阻塞，但其生命周期与 app 一致，且不能取消
 *    GlobalScope.launch {
 *      getImage(imageId)
 *    }
 */
fun globalScope() {
    println("start")
    GlobalScope.launch {
        launch {
            delay(300)
            println("launch A")
        }
        launch {
            delay(300)
            println("launch B")
        }
        println("GlobalScope")
    }
    println("end")
    Thread.sleep(400)  // 此处小于 300 ms 时就看不到 launch A/B 日志的输出
}

/**
 *    方法三，自行通过 CoroutineContext 创建一个 CoroutineScope 协程作用域对象，需要一个类型为 CoroutineContext 的参数
 *    等待其内部所有相同作用域的协程结束后才会结束自己，区别在于 runBlocking 方法会阻塞当前线程，而 coroutineScope不会阻塞线程，而是会挂起并释放底层线程以供其它协程使用
 *    val coroutineScope = CoroutineScope(context)
 *    coroutineScope.launch {
 *      getImage(imageId)
 *    }
 */
fun coroutineScope() {
    runBlocking {
        launch {
            delay(100)
            println("Task from runBlocking")
        }
        coroutineScope {
            launch {
                delay(500)
                println("Task from nested launch")
            }
            delay(100)
            println("Task from coroutine scope")
        }
        println("Coroutine scope is over")
    }
}

/**
 * Activity 中先后启动了多个协程用于执行异步耗时操作，那么当 Activity 退出时，必须取消所有协程以避免内存泄漏。我们可以通过保留每一个 Job 引用然后在 onDestroy方法里来手动取消
 * CoroutineScope 的实例可以通过 CoroutineScope() 或 MainScope() 的工厂函数来构建。前者创建通用作用域，后者创建 UI 应用程序的作用域并使用 Dispatchers.Main 作为默认的调度器
 * 使用 launch 或 async 创建的每个协程都会返回一个 Job 实例，该实例唯一标识协程并管理其生命周期
 * 通过 await() 方法可以拿到 async 协程的执行结果
 *
 * //当 Job 处于活动状态时为 true
 *  //如果 Job 未被取消或没有失败，则均处于 active 状态
 * public val isActive: Boolean

 * //当 Job 正常结束或者由于异常结束，均返回 true
 * public val isCompleted: Boolean

 * //当 Job 被主动取消或者由于异常结束，均返回 true
 * public val isCancelled: Boolean

 * //启动 Job
 * //如果此调用的确启动了 Job，则返回 true
 * //如果 Job 调用前就已处于 started 或者是 completed 状态，则返回 false
 * public fun start(): Boolean

 * //用于取消 Job，可同时通过传入 Exception 来标明取消原因
 * public fun cancel(cause: CancellationException? = null)

 * //阻塞等待直到此 Job 结束运行
 * public suspend fun join()

 * //当 Job 结束运行时（不管由于什么原因）回调此方法，可用于接收可能存在的运行异常
 * public fun invokeOnCompletion(handler: CompletionHandler): DisposableHandle
 */
fun customCoroutineScope() {
    class Activity {

        private val mainScope = MainScope()

        fun onCreate() {
            mainScope.launch {
                repeat(5) {
                    delay(1000L * it)
                }
            }
        }

        fun onDestroy() {
            mainScope.cancel()
        }
    }

    //委托模式来让 Activity 实现 CoroutineScope 接口，从而可以在 Activity 内直接启动协程而不必显示地指定它们的上下文，并且在 onDestroy()中自动取消所有协程
    class Activity2 : CoroutineScope by CoroutineScope(Dispatchers.Default) {

        fun onCreate() {
            launch {
                repeat(5) {
                    delay(200L * it)
                    println(it)
                }
            }
            println("Activity Created")
        }

        fun onDestroy() {
            cancel()
            println("Activity Destroyed")
        }

    }
}

/**
 * 创建一个使用了 SupervisorJob 的 coroutineScope，该作用域的特点就是抛出的异常不会连锁取消同级协程和父协程
 */
fun supervisorScope() {
    runBlocking {
        launch {
            delay(100)
            println("Task from runBlocking")
        }
        supervisorScope {
            launch {
                delay(500)
                println("Task throw Exception")
                throw Exception("failed")
            }
            launch {
                delay(600)
                println("Task from nested launch")
            }
        }
        println("Coroutine scope is over")
    }
}

suspend fun getImage() = withContext(Dispatchers.IO) {
}