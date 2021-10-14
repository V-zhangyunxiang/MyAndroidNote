package com.owl.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient2 {

  public static void main(String[] args) throws IOException {
    System.out.println("客户端启动...");
    Socket socket = new Socket(InetAddress.getByName("192.168.1.103"), 14444);
    // 读取键盘输入的小写字母
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // 向服务器发送小写字母
    OutputStream out = socket.getOutputStream();
    // BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(out));
    PrintWriter pw = new PrintWriter(out);
    // 接收大写字母
    InputStream in = socket.getInputStream();
    BufferedReader brr = new BufferedReader(new InputStreamReader(in));

    // 循环读取键盘输入的小写字母
    String line = null;
    while ((line = br.readLine()) != null) {
      if ("over".equals(line)) break;
      // bw.write(line);
      pw.println(line);
      // 接受大写字母
      System.out.println(brr.readLine());
    }
    // br.close();
    socket.close();
  }
}
