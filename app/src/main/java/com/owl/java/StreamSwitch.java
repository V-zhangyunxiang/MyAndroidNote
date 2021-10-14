package com.owl.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamSwitch {

  public static void main(String[] args) throws IOException {
    // 标准的输入流  --键盘
    // 标准输入流--控制台
    // 已经和键盘绑定好的一个InputStream类型的流对象
    InputStream in = System.in;
    OutputStream os = System.out;
    // int num=in.read();
    // System.out.println((char)num);

    // 循环读取键盘输入的数据，够一行时打印大写形式，不够一行时存储 (window \r\n)
    /*
    int num;
    StringBuilder sb=new StringBuilder();
    while(true){
    	num=in.read();
    	if(num=='\r')
    		continue;
    	else if(num=='\n'){
    		if("over".equals(sb.toString())) //输入 over 时结束
    			break;
    		System.out.println(sb.toString().toUpperCase());
    	    sb.delete(0, sb.length());
    	}
    	else
    		sb.append((char)num);
    }
    */

    // 如何把字节输入流转换为字符输入流?
    // 转换流:
    // 把字节输入流  转成 字符输入流的类: InputStreamReader
    // 把字节输出流  转成 字符输出流的类: OutputStreamWriter
    // 转换成字符后能使用 readLine,所以转.
    // 字符流不能被转换成字节流,直接用getByte就能转换了

    // System.setIn(new FileInputStream("b.txt"));//改变标准的输入
    // System.setOut(new PrintStream("a.txt")); //改变标准的输出
    // 把键盘输入的数写入到文件中去
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new FileWriter("b.txt"));
    String line;
    while ((line = br.readLine()) != null) {
      if ("over".equals(line)) break;
      bw.write(line);
      bw.newLine();
      bw.flush();
    }
    br.close();
    bw.close();
  }
}
