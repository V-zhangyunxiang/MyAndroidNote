package com.owl.kotlin.code

/**
 * Java 中目录和包的规则
 *  public 类必须和文件名相同
 *  一个 Java 文件只能有一个 public 类
 *  使用 package 声明一个包
 *  目录名通过 . 来分割
 *  声明的包结构必须与目录结构对应
 *
 * kotlin 中目录和包的规则
 *  Kotlin 中一个 kt 文件中可以放多个 public 的类
 *  使用  package 声明一个包
 *  目录名通过 . 来分割
 *  声明的包结构不需要与目录结构对应
 *    - import 时按照 kotlin 类中 package 结构来导入
 *    - 实际还是尽量让包声明和文件目录结构相对应，以免出现些未知错误
 */