package com.owl.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedWriterTest {
  /*
   * 字符流的缓冲区:缓冲区是为了提高读或写的效率，不具备读或写的功能
   *              使用缓存流必须结合者读流或写流
   * BufferReader
   * BufferWriter  从运行结果上看不出来效率的
   */
  public static void main(String[] args) throws IOException {
    // 使用缓冲实现文件的写入
    FileWriter fw = new FileWriter("b.txt");
    // fw利用缓冲流提高效率
    BufferedWriter bw = new BufferedWriter(fw);
    for (int i = 0; i < 3; i++) {
      bw.write("abc" + i);
      bw.newLine(); // 写入换行，跨平台的
      bw.flush();
    }
    bw.close(); // 关闭缓冲流就是关闭字符输出流
  }
}
