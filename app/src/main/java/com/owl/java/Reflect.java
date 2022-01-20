package com.owl.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Reflect {
  /*
   * 反射:动态获取类(字节码文件),并对其成员进行运行,不需要 new 对象，就能操作类中的成员(有些情况不能new,只能用反射)
   * 获取类的共同性:1.构造方法;         2.属性;         3.方法
   *               getConstructor    getField      getMethod
   * 获取 Class 的方式:
   * 1.使用 object 提供的 Class<?> getClass() 方法得到字节码文件(需要事先存在对象)
   * 2.每种属性类型都有一个 Class 属性,通过该属性得到的就是字节码文件对象(需要这种类型事先存在)
   * 3.使用 Class 的静态方法 forName(""),字符串由 包名+类名 构成(只需提供一个字符串) （常用）
   *
   * 反射一般结合 XML 或者 properties 文件使用，提高程序的可扩展性
   */
  public static void main(String[] args)
      throws ClassNotFoundException, NoSuchFieldException, SecurityException,
          InstantiationException, IllegalAccessException, NoSuchMethodException,
          IllegalArgumentException, InvocationTargetException {
    // getClass3();

    // 得到字节码文件
    Class<?> cla = Class.forName("reflect.Person");
    // 创建类对象，Object 可以写成某个具体类
    // 第一种：通过 Class 对象的 newInstance() 方法。
    Object obj1 = cla.newInstance();
    // 第二种：通过 Constructor 对象的 newInstance() 方法
    Constructor constructor = cla.getConstructor();
    Object obj2 = constructor.newInstance();

    // 得到属性
    // Field field=claz.getField("name");//只能得到public权限的属性
    Field field2 = cla.getDeclaredField("name"); // 可得到任何权限的属性
    field2.setAccessible(true); // 设置可以访问,不然无法赋值
    field2.set(obj2, "大伞"); // name是非静态，需要用对象obj赋值
    System.out.println(obj2);

    // 得到方法
    //    Method method = cla.getMethod("show", null); // getMethod(方法名,方法的参数类型,如int.class)
    //    // Method method=claz.getDeclaredMethod("show", null);
    //    method.setAccessible(true); // 设置可以访问,不然无法调用
    //    method.invoke(obj2, null); // invoke(对象,给方法传的实参)
  }

  public static void getClass1() {
    Person ren = new Person();
    Class<? extends Person> claz = ren.getClass();
    // 不管创建多少个对象,getClass()都只能获取同一个class
  }

  public static void getClass2() {
    Class<Person> claz = Person.class;
  }

  public static void getClass3()
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    Class<?> claz = Class.forName("reflect.Person"); // 得到字节码对象
    // System.out.println(claz);
    Object obj = claz.newInstance(); // 创建类对象-使用无参的构造方法
  }

  public static void createObj()
      throws ClassNotFoundException, NoSuchMethodException, SecurityException,
          InstantiationException, IllegalAccessException, IllegalArgumentException,
          InvocationTargetException {
    Class<?> claz = Class.forName("reflect.Person"); // 得到字节码对象
    // 创建类对象,调用有参的构造方法
    Constructor con = claz.getConstructor(String.class, int.class);
    Object obj = con.newInstance("李四", 23);
  }
}
