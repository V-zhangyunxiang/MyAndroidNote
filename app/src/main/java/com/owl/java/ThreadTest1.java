package com.owl.java;

public class ThreadTest1 {
  /* 多线程
   * 进程:正在运行的程序，在内存中开辟的一块空间  线程:负责程序执行的一条路径
   * 进程的执行实际上是线程在执行
   * 一个进程至少会有一个线程
   * 当一个进程中有多个线程，就是多线程程序  好处:可以实现不同功能的同时执行
   * 多线程不一定能提高效率，但是能合理使用cpu资源，最主要是能实现同时执行的效果
   * jvm 是多线程的
   * 垃圾回收机制:调用 finalize() 方法，回收没有引用的实例对象
   */
  public static void main(String[] args) { // 主线程
    /* 任务:每个线程需要执行的代码,任务代码都有其存储位置
     *    主线程任务代码在 main 函数中
     *    垃圾回收线程在 finalize 函数中
     *    每个线程是独立的,一个线程异常,不会影响其他线程
     *    线程随着任务的存在而存在，随着任务的消失而消失
     */
    // 每次运行的结果不一样，因为线程在争抢 cpu，这就是多线程程序的随机性
    System.gc(); // 让垃圾回收线程去执行，这时有两个线程
    System.out.println("hello world");
    // run 函数就是子线程任务代码的储存位置
    Thread t =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                // Thread.currentThread().getName();
                // 返回当前正在执行的线程对象的名字,主线程名字为 main,子线程为 Thread-编号，从0开始
              }
            });
    // 当所有线程结束，整个进程才结束
  }
}
