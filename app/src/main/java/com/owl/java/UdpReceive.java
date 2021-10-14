package com.owl.java;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpReceive {
  /* 使用 Udp 实现数据的接收
   * 1.创建 Socket 端点
   * 2.创建一个空的数据报包来接收数据
   * 3.使用 Socket 的接收功能接收数据
   * 4.获取接收到的数据
   */
  public static void main(String[] args) throws IOException {
    // 1.创建 Socket 端点
    DatagramSocket socket = new DatagramSocket(11111); // 接收方必须监听发送方的端口，才能收到数据
    // 2.创建一个空的数据报包来接收数据
    byte[] arr = new byte[1024];
    DatagramPacket packet = new DatagramPacket(arr, arr.length);
    // 3.使用 Socket 的接收功能接收数据
    socket.receive(packet);
    // 获取接收到的数据
    byte[] data = packet.getData();
    // 字节数组解码成字符串
    String content = new String(data, 0, packet.getLength());
    // 获取发送数据的主机 ip
    InetAddress address = packet.getAddress();
    String ip = address.getHostAddress(); // ip比 address 前面多了一个/
    // 得到发送方使用的端口号
    int port = packet.getPort();
    System.out.println(
        "ip:" + ip + ";" + "port:" + port + ";" + "内容为:" + content + ";" + "adress:" + address);
    socket.close();
    // 先运行接收方，在运行发送方
  }
}
