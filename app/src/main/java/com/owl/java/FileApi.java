package com.owl.java;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileApi {
  // File类解析
  public static void main(String[] args) {
    // method();
    method2();
  }

  public static void method() throws IOException {
    File file = new File("zyx.txt");
    if (!file.exists()) file.createNewFile(); // 创建文件               文件带后缀名，目录是文件夹
    // file.mkdir();//创建目录
    // file.mkdirs();//创建多层目录                  mkdirs能创建多级文件夹目录,mkdir只能创建一个
    sop(file.getName());
    sop(file.getPath());
    sop(file.getParent()); // 得到上一级路径
    sop(file.getAbsolutePath()); // 得到绝对路径
    sop(file.isFile());
    sop(file.isDirectory()); // 是否是一个目录
    sop(file.isHidden());
    sop(new Date(file.lastModified())); // 最后修改的时间
  }

  public static void method2() {
    File[] dir0 = File.listRoots(); // 列出系统所有的盘
    for (File dir : dir0) {
      sop(dir);
    }
    File dir1 = new File("f:\\"); // 列出指定目录下所有文件和文件夹,以字符串形式返回
    String[] arr = dir1.list();
    for (String ss : arr) {
      sop(ss);
    }
    File dir2 = new File("f:\\ali"); // 列出指定目录下所有文件和文件夹,以File形式返回
    File[] f = dir2.listFiles();
    for (File aa : f) {
      sop(aa);
    }
  }
  // 递归:自己调用自己
  // 需要注意的问题: 1:栈中过多函数,内存溢出  2:要有结束条件
  // 使用环境:列出 f 盘所有文件和其文件夹子目录内容
  public static void sop(Object obj) {
    System.out.println(obj);
  }
}
