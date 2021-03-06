package com.owl.kotlin.code

/**
 * 1.计算机切换分为两种状态：
 *   挂起：保存程序的当前状态，暂停当前程序
 *   激活：恢复程序状态，继续执行程序
 * 2.同步和异步的关注点是 是否同时进行，而堵塞和非堵塞关注的是 能否继续进行
 * 3.进程是系统进行 资源分配和调度 的一个 独立单位
 *   进程的出现使得多个程序得以 并发 执行，提高了系统效率及资源利用率，但存在下述问题：
 *   ① 单个进程只能干一件事，进程中的代码依旧是串行执行。(同步)
 *   ② 执行过程如果堵塞，整个进程就会挂起，即使进程中某些工作不依赖于正在等待的资源，也不会执行(易阻塞)
 *   ③ 多个进程间的内存无法共享，进程间通讯比较麻烦
 * 4.为了解决上述问题，线程出现了，为了降低切换的消耗，提高系统的并发性，使得进程内并发变成可能
 * 5.线程 VS 进程
 *   进程」是「资源分配」的最小单位，「线程」是 「CPU调度」的最小单位
 *   ① 一个程序至少有一个进程，一个进程至少有一个线程，线程依赖于进程
 *   ② 进程在执行过程中拥有 独立的内存单元，同一个进程内多个线程 共享内存
 *   ③ 进程可以拓展到 多机，线程最多适合 多核
 * 6.并发 VS 并行
 *   1.并发是指一个处理器同时处理多个任务，当处理速度足够快时，看起来像是同时进行
 *   2.并行是指多个处理器或者是多核的处理器同时处理多个不同的任务
 * 7.协作式 VS 抢夺式
 *   1.早期的操作系统采用的是协作式多任务，由进程主动让出执行权，当前进程执行完后才能轮到下一个进程，单个进程可能会出现霸占 CPU 的情况
 *   2.此时就出现了现在操作系统采用的抢夺式，由操作系统决定执行权，系统为每一个进程分配时间片，每个进程用完就休眠，甚至时间片没用完
 *     如果有更紧急的进程要执行，也会强制让前一个进程休眠，但这也带来了新的问题 —— 线程安全
 * 8.单线程的 Android GUI 系统
 *   如果采用多线程，多个线程同时对一个 UI 控件更新，就会发生线程同步安全问题，这时你会想到加锁，但这会产生更多的耗时和 UI 更新效率降低，还有可能产生死锁
 *   多线程模型带来的复杂度成本，远远超出它所提供的性能优势，所以 Android 建议在主线程更新 UI
 * 9.Android 目前异步更新 UI 的方式
 *   1.子线程中 handler 发射 Message
 *   2.AsyncTask
 *     ① AsyncTask类需在主线程中加载
 *     ② AsyncTask对象需在主线程中创建
 *     ③ execute() 必须在主线程中调用，且一个 AsyncTask 对象只能调用一次此方法
 *     ④ 需要为每一种任务类型创建一个特定子类，同时为了访问 UI 方便，经常定义为 Activity 的内部类，耦合严重
 *   3.runOnUiThread(handler.post)
 *   4.RxJava
 *   5.LiveData + ViewModel
 *     1.LiveData 是 Jetpack 提供的一种响应式编程组件，可以包含任何类型的数据，并在数据发生变化时通知给观察者
 *     2.感知并遵循 Activity、Fragment 或 Service 等组件的生命周期，可以仅在组件处于激活状态时才更新 UI
 *   6.协程
 *     1.协作式的任务调度模式，程序可以主动挂起和恢复
 *     2.协程基于线程，一个线程可以运行多个协程，提高了线程的并发效率，避免了大量的回调嵌套
 *     3.使用 withContext 函数可以切换到指定的线程，并在闭包内的逻辑执行结束后，自动把线程切换回上下文继续执行，有一种同步方式写异步代码的味道
 *     按调用栈
 *      有栈调用：每个协程会分配单独的调用栈，可以在任意函数嵌套中挂起，类似线程的调用栈
 *      无栈调用：不会分配单独的调用栈，只能在当前函数挂起，挂起点状态通过闭包或对象保存
 *     按调用关系：
 *      对称协程：  调度权可以转移给任意协程，协程之间是对等关系
 *      非对称协程：调度权只能转移给调用自己的协程，存在父子关系
 */