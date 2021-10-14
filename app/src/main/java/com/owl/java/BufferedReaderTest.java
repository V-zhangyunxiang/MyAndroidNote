package com.owl.java;

import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderTest {

  public static void main(String[] args) throws IOException {
    // 适用缓冲流读取文件
    FileReader fr = new FileReader("b.txt");
    java.io.BufferedReader br = new java.io.BufferedReader(fr);
    String line;
    // line=br.readLine();//一次读一行，并返回该字符串表示,结果不包含最后的换行符
    while ((line = br.readLine()) != null) {
      System.out.println(line); // 要人为的加上ln换行
    }
  }
}
