package com.owl.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpContinueSend {
  /* 使用 udp 协议持续发送数据
   * 最大不超过 64k
   */
  public static void main(String[] args) throws IOException {
    DatagramSocket socket = new DatagramSocket();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;
    while ((line = br.readLine()) != null) {
      if ("over".equals(line)) break;
      byte[] data = line.getBytes();
      DatagramPacket packet =
          new DatagramPacket(data, data.length, InetAddress.getByName("192.168.1.103"), 22222);
      socket.send(packet);
      socket.close();
    }
  }
}
