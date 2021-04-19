package com.owl.java;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/9/28 14:52
 */
public class StaticExtendsActivity {

  public static void main(String[] args) {
    C c = new D();
    System.out.println(c.a);
    System.out.println(c.c);
    C.ss();
    c.print();

    //    D d = new D();
    //    System.out.println(d.a);
    //    System.out.println(d.c);
    //    D.ss();
    //    d.print();
  }
}

class C {
  int a = 1;
  static int c = 2;

  void print() {
    System.out.println("print C");
  }

  public static void ss() {
    System.out.println("SC");
  }
}

class D extends C {
  int a = 111;
  static int c = 222;

  void print() {
    System.out.println("print D");
  }

  public static void ss() {
    System.out.println("SD");
  }
}
