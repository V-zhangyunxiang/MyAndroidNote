package com.xiaozhu.java;

/**
 * Description
 *
 * @author zhangyunxiang Date 2020/5/9 18:01
 */
public class AA extends BB {

  public AA() {
    System.out.print(" AA ");
  }

  @Override
  public void printF() {
    System.out.print(" A-F ");
    super.printF();
  }
}
