package com.owl.kotlin.code

/**
 * kotlin 类、成员、方法默认权限都是 public
 *
 * public     全局可见
 * internal   模块内可见- 一个 jar 包/一个 aar, 修饰的类和成员在 Java 中可直接访问(internal 在 java 中会被识别为 public)，可通过 @JvmName 注解设置方法/属性别名
 * private    本类中可见
 * protected  本类和子类可见
 *
 * 属性可见性
 *   property 为 public , 私有属性的 set，外部只能读取
 *   property 为 private, 不能私有属性的 set
 *
 * 不在类中方法和属性为顶级函数或属性
 *
 * 顶层声明
 *    消除了 Java 中的静态工具类，独立于类存在的函数或属性
 *    不支持 protected，private 表示文件内可见，不能访问外部类的 private 成员
 *    kotlin 中通过 import 函数路径后直接使用
 *    Java 会将顶层函数所在的 kt 文件名.函数 来调用，属性同理
 *    在文件的开头，包名的前面使用 @JvmName 自定义类名，提供给 Java 调用,属性同理
 *    不同的 kt 文件中，相同的顶层函数或属性间冲突
 * */





