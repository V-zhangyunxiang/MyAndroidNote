package com.owl.java;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamOperate {

  public static void main(String[] args) throws IOException {
    // 使用字节输出流向文件中写入数据
    // 1.与文件绑定
    // FileOutputStream fos=new FileOutputStream("bCopy.txt");
    // 2.转换为字节写入
    // fos.write("kkk".getBytes());
    // fos.close();

    // 使用字节输入流读取文件数据
    // FileInputStream fis=new FileInputStream("b.txt");
    // int num;
    // while((num=fis.read())!=-1){
    //	System.out.print((char)num);
    // }
    // int size=fis.available();//返回文件的总大小,替代1024,不适合大文件
    // byte[] b=new byte[1024];
    // while((num=fis.read(b))!=-1){
    // System.out.print(new String(b,0,num));
    // }

    // 复制一张图片
    FileInputStream fis = new FileInputStream("diqiu.jpg");
    FileOutputStream fos = new FileOutputStream("diqiucopy.jpg");
    int num1;
    byte[] arr = new byte[1024];
    while ((num1 = fis.read(arr)) != -1) {
      fos.write(arr, 0, num1);
    }
    fis.close();
    fos.close();
    // 字节缓冲流与字符缓冲流不同的是没有 readLine 和 newline，与普通的读取写入流程一样
    // BufferedInputStream
    // BufferedOutputStream
  }
}
