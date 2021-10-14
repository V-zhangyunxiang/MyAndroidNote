package com.owl.java;

import java.io.FileWriter;
import java.io.IOException;

public class IoException {
  // IO流异常处理
  /*
  * 如果对可能出现的 exception 不想被外部（方法的调用者）知道,就在方法内部 try－catch 掉这个 exception
  * 如果希望外部知道，则在 catch 到之后把 exception 直接抛出
  * Exception 和 RuntimeException(虽然 RuntimeException 是从 Exception 继承的)。
       exception 异常代表“无法避免的异常” 如io异常   往往这类异常是由于外部原因造成的,程序本身无法保证他们不发生，所以这类异常必须捕获
  *    runtimeException 指“可以避免的异常”，如 null引用异常,这类异常都是由程序内部原因造成的,是可以避免的.对于这类异常可以忽略他们，但一旦发生程序就会异常终止
  */
  public static void main(String[] args) {
    FileWriter fw = null;
    try {
      fw = new FileWriter("k:\\temp.txt"); // FileNotFoundException
      fw.write("kk"); // 因为路径错误，导致fw未创建成功
      fw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fw != null) fw.close(); // NullPointerException
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
