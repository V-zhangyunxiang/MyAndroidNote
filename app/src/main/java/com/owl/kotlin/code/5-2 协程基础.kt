package com.owl.kotlin.code

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.system.measureTimeMillis

/**
 *  1.什么是协程？
 *    协程就是挂起和恢复
 *  2.协程的作用？
 *    异步逻辑，同步代码
 *  3.什么是上下文环境？
 *    1.简单理解：完成某件事物时所需的前置资源
 *    2.扩展到 线程切换上下文环境
 *      线程Id + 线程状态 + 堆栈 + 寄存器状态等
 *    3.扩展到 Kotlin 协程中的上下文环境 → CoroutineContext，以 键值对 的方式存储各种不同元素
 *      Job(协程唯一标识) + CoroutineDispatcher(调度器) + ContinuationInterceptor(拦截器) + CoroutineName(协程名称，一般调试时设置)
 *      Job、调度器、拦截器，协程名称都是属于 CoroutineContext 上下文环境，如果需要为协程定义多个上下文元素，使用 + 运算符
 *      + 拼接时相同设置右侧元素会覆盖左侧元素
 *  4.什么是结构化并发？
 *    协程必须在作用域中才能启动，作用域中定义了一些父子协程的规则，Kotlin 协程通过作用域来管控域中的所有协程，作用域间可并列或包含，组成一个树状结构
 *  5.作用域
 *    顶级作用域：没有父协程的协程所在的作用域
 *    协同作用域：协程中启动新协程(子协程)，此时子协程所在的作用域默认为协同作用域，子协程抛出的未捕获异常都将传递给父协程处理，父协程同时也会被取消
 *    主从作用域：与协同作用域父子关系一致，区别在于子协程出现未捕获异常时不会向上传递给父协程
 *  6.父子协程间规则
 *    父协程被取消，所有子协程均被取消
 *    父协程需等待子协程执行完毕后才会最终进入完成状态，而不管父协程本身的协程体是否已执行完
 *    子协程会继承父协程上下文中的元素，如果自身有相同 Key 的成员，则覆盖对应 Key，覆盖效果仅在自身范围内有效
 *  7.协程取消
 *    1.取消线程一般采用标志位方式，使用前判断当前协程是否存活 -> isActive，取消 -> cancel()
 *    2.已取消的作用域无法再创建协程
 *    3.父协程取消会递归取消所有子协程
 *  8.创建作用域(GlobalScope runBlocking 自定义作用域)
 *  9.协程构造器，CoroutineScope 的扩展方法
 *    launch & async 创建一个「不堵塞」当前线程的新协程
 *    launch 返回一个 Job，用于协程监督与取消，用于无返回值的场景
 *    async 返回一个 Job 的子类 Deferred，可通过 await() 获取完成时返回值，await()会阻塞当前线程直到协程返回结果值
 *  10.suspend 挂起函数
 *     用于定义一个挂起函数，它就是一个标记，告知编译器，这个函数需在协程中执行，仅仅使用 suspend 并不会真的挂起，要在 suspend 函数内部调用某个自带的挂起函数
 *     对应的恢复为 resumeWith
 *  11.Job
 *     含义
 *      调用 launch 函数会返回一个 Job 对象，代表一个协程的工作任务
 *     常用 API
 *      isActive: Boolean    //是否存活
 *      isCancelled: Boolean //是否取消
 *      isCompleted: Boolean //是否完成
 *      cancel()             // 取消协程，不会立即停止，取消后再次使用时需要检查协程是否处于活动状态
 *      join()               // 堵塞当前线程直到协程执行完毕
 *      cancelAndJoin()      // 两者结合，取消并等待协程完成
 *     生命周期
 *       New创建 - Active活跃 - Complete完成
 *  12.异常处理
 *     1.try-catch 直接捕获作用域内异常
 *     2.全局异常处理(只支持 launch 传入 CoroutineExceptionHandler)
 *     3.异常传播，协程作用域中异常传播默认是双向的
 *       父协程发生异常，所有子协程都会取消
 *       子协程发生异常，会导致父协程取消，间接导致兄弟协程也取消
 *     4.变成单向
 *       1.用 SupervisorJob 代替 Job
 *       2.用 supervisorScope 代替 CoroutineScope
 *  13.启动模式
 *     CoroutineStart
 *     DEFAULT 创建后立即开始调度，调度前被取消，直接进入取消响应状态(并行)
 *     LAZY    懒加载，不会立即开始调度，需要手动调用 start、join 或 await 才会开始调度，如果调度前就被取消，协程将直接进入异常结束状态(串行)
 *  14.调度器
 *     Dispatchers.Default    共享后台线程池里的线程
 *     Dispatchers.Main       Android 主线程
 *     Dispatchers.IO         IO 密集型的任务
 *     Dispatchers.Unconfined 不限制，使用父 Coroutine 的线程
 *     Dispatchers.newSingleThreadContext 使用新的线程
 *     当 launch {...} 在不带参数的情况下使用时，它从外部的协程作用域继承上下文和调度器，即和 runBlocking 保持一致
 *     而在 GlobalScope 中启动协程时默认使用的调度器是 Dispatchers.default
 *  15.withContext()
 *     withContext 不会创建新的协程，常用于切换线程
 *     它也是一个挂起方法，直到结束返回结果
 *     多个 withContext 是串行执行的，适用于下一个任务依赖上一个任务的结果
 *  16.拦截器
 *     协程启动时，挂起时，返回结果时会触发拦截器代码
 *  17.channel
 *  18.Flow
 *
 *
 *
 */
fun main() {
    // runBlocking()
    //globalScope()
    //mainScope()
//    val mainActivity = MainActivity()
    //mainActivity.test()
    // supervisorScope()
    // Interceptor()
    startMode()

    //源码示例
//    suspend { }.startCoroutine(object : Continuation<Unit> {
//        override val context: CoroutineContext
//            get() = EmptyCoroutineContext
//
//        override fun resumeWith(result: Result<Unit>) {
//            TODO("Not yet implemented")
//        }
//
//    })
}

/**
 *    方法一 使用 runBlocking 顶层函数，线程阻塞(类似 thread.sleep)，但其内部协程是非阻塞的
 *    runBlocking 只有当内部 相同作用域 的所有协程全部结束后，在 runBlocking 后的代码才能执行
 *    runBlocking 只会等待 相同作用域 的协程完成才会退出，而不会等待 GlobalScope 等其它作用域启动的协程
 *    runBlocking {
 *     getImage(imageId)
 *    }
 */
fun runBlocking() {
    println("start->${Thread.currentThread().name}")
    runBlocking {
        launch() {
            delay(100)
            println("launchA ->${Thread.currentThread().name}")
        }
        launch() {
            delay(100)
            println("launchB ->${Thread.currentThread().name}")
        }
        GlobalScope.launch {
            delay(120)
            println("GlobalScope ->${Thread.currentThread().name}")
        }
        println("runBlocking->${Thread.currentThread().name}")
    }
    println("end->${Thread.currentThread().name}")
}

fun runBlocking2() {
    GlobalScope.launch(Dispatchers.IO) {
        delay(600)
        println("GlobalScope")
        async {

        }
    }
    runBlocking {
        delay(500)
        println("runBlocking")
    }
    //主动休眠两百毫秒，使得和 runBlocking 加起来的延迟时间少于六百毫秒
    Thread.sleep(200)
    println("after sleep")
}


/**
 *    方法二 使用 GlobalScope 全局作用域单例对象，非线程阻塞，但其生命周期与 app 一致
 *    默认使用的调度器是 Dispatchers.default
 *    GlobalScope.launch {
 *      getImage(imageId)
 *    }
 */
fun globalScope() {
    println("start->${Thread.currentThread().name}")
    GlobalScope.launch {
        launch {
            delay(200)
            println("launch A->${Thread.currentThread().name}")
        }
        launch {
            delay(300)
            println("launch B->${Thread.currentThread().name}")
            launch {
                //delay(70)
                println("launch C->${Thread.currentThread().name}")
            }
        }
        println("GlobalScope->${Thread.currentThread().name}")
    }
    println("end->${Thread.currentThread().name}")
    Thread.sleep(400)  // 此处小于 300 ms 时就看不到 launch A/B 日志的输出, JVM 保活
}

/**
 *    方法三，自定义作用域
 *    1.让类实现 CoroutineScope 接口，让该类成为一个协程作用域，非线程阻塞
 *    2.使用 MainScope() 函数，Dispatchers.Main 作为默认的调度器
 *    3.使用 coroutineScope() 和 supervisorScope() 创建子作用域，只能在一个已有的协程作用域中调用
 *      都是挂起函数
 *      前者出现异常时会把异常抛出(父协程及其他子协程会被取消)，后者出现异常时不会影响其他子协程
 */
class MainActivity(override val coroutineContext: CoroutineContext = EmptyCoroutineContext) : CoroutineScope {
    fun test() {
        println("1->${Thread.currentThread().name}")
        launch {
            println("2->${Thread.currentThread().name}")
            loadUrl()
            refreshUI()
        }
        Thread.sleep(400)
    }

    private suspend fun loadUrl(): Unit = coroutineScope {
        println("loadUrl->${Thread.currentThread().name}")
    }

    private suspend fun refreshUI() = supervisorScope {
        println("refreshUI->${Thread.currentThread().name}")
    }
}

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

fun startMode() {
    val time = measureTimeMillis {
        runBlocking {
            val asyncA = async() {
                delay(3000)
                1
            }
            val asyncB = async() {
                delay(4000)
                2
            }
            println(asyncA.await() + asyncB.await())
        }
    }
    println(time)
}

fun mainScope() {
    val mainScope = MainScope()
    //Dispatchers.setMain(Dispatchers.Main)
    mainScope.launch() {
        println("mainScope-1->${Thread.currentThread().name}")
        launch {
            println("mainScope-2->${Thread.currentThread().name}")
            delay(500)
            println("Task from main child scope")
        }
        delay(100)
        println("Task from mainScope")
    }
    println("Coroutine scope is over")
}

fun Interceptor() {
    GlobalScope.launch(MyInterceptor()) {
        val ss = async {
            delay(200)
            println("Interceptor")
            "嘿嘿嘿"
        }
        val result = ss.await()
        println("result = $result")
    }
}


class MyInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return MyContinuation(continuation)
    }

}

class MyContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> {
    override val context = continuation.context
    override fun resumeWith(result: Result<T>) {
        println("result= $result")
        continuation.resumeWith(result)
    }

}

suspend fun getImage() = withContext(Dispatchers.IO) {

}

