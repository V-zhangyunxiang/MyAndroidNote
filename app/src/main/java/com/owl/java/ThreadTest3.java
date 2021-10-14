package com.owl.java;
/*
* sleep和 wait区别:
* 1.这两个方法来自不同的类分别是Thread和Object
　  2.最主要是sleep方法没有释放锁,而wait方法释放了锁,使得其他线程可以使用同步控制块或者方法。
　  3.wait,notify和notifyAll只能在同步控制方法或者同步控制块里面使用,而sleep可以在
　       任何地方使用。
　       synchronized(x){  //x为锁对象
　       x.notify()//唤醒
　       x.wait()
         x.nofityAll()
　       }
　  4.sleep,wait必须捕获异常,而notify和notifyAll不需要捕获异常
*/

// 多程序安全问题:1.数据由于线程互相抢夺cpu,在等待时可能被改变了某些条件参数，
//             当一个线程被唤醒时就跳过了这些过滤条件，造成结果不合理
//           2.是跟线程对象个数有关的
// 不同的线程之间可以在进程范围内共享数据,也就是说进程有自己独立的存储空间，而线程是和它所属的进程内的其他线程共享一个存储空间
// 锁有效果的两个必要条件:1.同时同步两个线程,2.同一把锁   (锁对象一般是共享数据对象,才能排斥)

// 如何解决线程安全问题?(同步方法,同步块)
/*
  1.当任意一个线程进入到一个锁对象的任意一个同步方法时,这个锁对象的所有同步方法都被锁定了
            在此期间,其他任何线程都不能访问这个锁对象的任意一个同步方法
            直到这个线程执行完它所调用的同步方法并从中退出(这样就能防止A线程在执行中,被B线程抢走,造成不安全问题)
  2.在一个对象被某个线程锁定之后,其他线程是可以访问这个对象的所有非同步方法的
* 3.同步代码块的锁一般是共享的数据对象
* 4.同步函数的锁是->this,静态同步函数的锁->是其所属类的字节码文件对象
* 5.单例模式的懒汉式就容易出现线程安全问题,而饿汉式就不会出现
*/
class Ticket2 implements Runnable {
  private int num = 50; // 因为传递的是同一个类对象，不需要设置成静态

  @Override
  public void run() {
    while (true) {
      if (num > 0) {
        System.out.println(Thread.currentThread().getName() + "...sale" + num--);
      }
    }
  }
}

public class ThreadTest3 {

  public static void main(String[] args) {
    Ticket2 t = new Ticket2(); // 任务,线程和任务进行了分离

    Thread t1 = new Thread(t);
    Thread t2 = new Thread(t);
    Thread t3 = new Thread(t);
    t1.start();
    t2.start();
    t3.start();
  }
}
