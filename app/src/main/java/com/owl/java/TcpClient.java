package com.owl.java;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpClient {

  public static void main(String[] args) throws IOException {
    // 创建 Socket 端点,同时监听端口
    System.out.println("服务端启动。。。。");
    ServerSocket server = new ServerSocket(13333);
    // 得到客户端使用的 Socket 流,这个 Socket 流就是客户端对象
    // 保证服务器端和客户端使用同一个Socket流
    Socket s = server.accept();

    // 接收数据
    InputStream in = s.getInputStream();
    // 读取数据
    byte[] arr = new byte[1024];
    int len = in.read(arr);
    System.out.println(new String(arr, 0, len));
    s.close();
  }
}
