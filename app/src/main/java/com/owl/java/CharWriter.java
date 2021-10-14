package com.owl.java;

import java.io.FileWriter;
import java.io.IOException;

// 字符流它们的长相与称呼相反
// 字节流它们的长相与称呼相同

// 字符流功能与长相相同
// 字节流功能与长相相反
/*
 * 字节流:可以操作任何类型的数据实现输入和输出
 *      文本，音频，视频，图片
 *
 * 字符流:只能操作文本实现输入和输出
 *
 * 为什么有了字节流还要用字符流?
 *   在字节流的基础上融入了编码，方便了文本的读写
 *
 * 字节流:两个父类
 *       字节输入流的父类-InputStream
 *        字节流输出流的父类-OutputStream
 * 字符流:两个父类
 *       字符输入流的父类-Reader
 *       字符输出流的父类 -Writer
 *
 *  1.先去确定是输入还是输出
 *  2.用字符流还是字节流
 *  3.用哪种类型的字符(字节)输入(输出)流
 */
public class CharWriter {

  public static void main(String[] args) throws IOException {
    // 因为是文本所以使用字符流，因为是从内存写入文件所以是输出流，因为是文件，所以用可以向
    // 文件写入数据的字符输出流 FileWriter

    // 1.创建文件写入流对象和被写入数据的文件关联
    FileWriter fw = new java.io.FileWriter("b.txt");
    // FileWriter fw=new FileWriter("b.txt",true);
    // 默认 false，如果目标文件已经存在，那么默认情况下会先清空文件中的数据，然后再写入数据
    // true 假如存在，不覆盖，追加数据;

    // 2.使用输出流对象向文件中写入数据
    // 数据写入到输出流对象的内部数组中了，是一个字节数组，因为要查编码表，把查表结果写入文件
    fw.write("abcd");
    fw.flush(); // 把内部缓冲中的数据刷新到文件中,可以继续写入
    fw.close(); // 因为是调用系统的写入功能，占用了底层资源，需要关闭流,
    // 还会刷新数据到文件中,但是此时不能再次写入，已经关闭了流
  }
}
