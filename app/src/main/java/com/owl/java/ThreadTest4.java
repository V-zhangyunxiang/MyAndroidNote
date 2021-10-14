package com.owl.java;

/* 线程间通信:线程的任务不同,但是线程操作的数据相同
 * 描述数据
 * wait():把线程放入线程池中
 * notify():唤醒线程池中的任意一个线程  不管 wait 还是 notify 都是针对持有该锁的任意一个线程
 * notifyAll():唤醒所有线程
 * join():加入一个线程,也用来让某个线程立刻抢到 CPU
 * 线程的停止:stop() 已过时，只能是当前线程任务代码执行完成自然结束
 *         1.任务代码一般都有循环，停止循环就能停止线程(flag=true or false)
 *         2.当线程等待很长时间,也就是调用 wait() 了,要用线程对象在外部调用 interrupt(),同时改变flag值,强制结束等待
 * 守护线程:也就是后台线程，依赖于前台线程,当前台线程结束时，守护线程也立即结束，如:垃圾回收线程。
 *
 * 多生产或者多消费时,用 while() 代替 if() 的唤醒等待条件解决线程不安全问题,用 notifyAll 唤醒所有线程解决死锁问题
 * jdk1.5之前对锁的操作是隐式的  synchronized(对象){}
 * jdk1.5对锁的操作是显式的:
 *   一个描述锁的接口 Lock 接口，把锁面向对象了
 *   使用 lock 替代同步代码块的方式:
 *   1:使用lock接口的子类 Reentrantlock 创建一把锁
 *   2:把之前写在同步代码块中的代码写在 lock() 和 unlock() 之间   lock.lock();
 *   3:unlock() 要在try finally中保证一定执行                           lock.unlock();
 *   4:wait 和 notify 要用 condition 接口对象实现
 *   Lock lock = new ReentrantLock();
 *   Condition con=lock.newCondition();   --用两个不同的 Condition 对象控制不同的任务
 *   Condition pro=lock.newCondition();     生产等待,消费执行;消费执行,生产等待(提高性能)
 *   con.await(); pro.signal(); signalAll();
 */

class Res {
  String name;
  String sex;
  boolean flag;
}

// 描述输入任务
class Input implements Runnable {
  private Res res;

  public Input(Res res) {
    this.res = res;
  }

  public void run() {
    int i = 1;
    while (true) {
      synchronized (res) {
        // 先判断该不该存
        if (res.flag)
          try {
            res.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        if (i == 1) {
          res.name = "贾冬冬";
          res.sex = "男";
        } else {
          res.name = "达利园";
          res.sex = "女";
        }
        res.flag = true;
        res.notify(); // 唤醒对方
      }
      i = (i + 1) % 2; // 在 0 和 1 之间循环
    }
  }
}

// 描述输出任务
class Output implements Runnable {
  private Res res;

  public Output(Res res) {
    this.res = res;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (res) {
        if (!res.flag)
          try {
            res.wait();
          } catch (InterruptedException e) {

            e.printStackTrace();
          }
        System.out.println(res.name + ",,," + res.sex);
        res.flag = false;
        res.notify();
      }
    }
  }
}

public class ThreadTest4 {

  public static void main(String[] args) {
    // 创建资源对象
    Res res = new Res(); // 共享的对象资源
    // 创建输入对象
    Input input = new Input(res);
    // 创建输出对象
    Output output = new Output(res);
    // 创建输入线程
    Thread t1 = new Thread(input);
    // 创建输出线程
    Thread t2 = new Thread(output);

    t1.start();
    t2.start();
    //		t2.setDaemon(true);//设置为守护线程
  }
}
