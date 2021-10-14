package com.owl.java;
/*
 * 卖票代码
 */
class Ticket extends Thread {
  private static int num = 50; // 共享这些票，所以要静态

  public void run() {
    boolean istrue = true;
    while (istrue) {
      if (num > 0) {
        System.out.println(Thread.currentThread().getName() + "...sale" + num--);
      } else {
        istrue = false;
      }
    }
  }
}

public class ThreadTest2 {

  public static void main(String[] args) {
    Ticket t1 = new Ticket();
    Ticket t2 = new Ticket();
    Ticket t3 = new Ticket();
    Ticket t4 = new Ticket();
    t1.start();
    t2.start();
    t3.start();
    t4.start();
  }
}
