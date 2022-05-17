package com.owl.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description
 *
 * @author zhangyunxiang Date 2019-12-16 10:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Person {
  int id() default 1;

  String role() default "s";
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Check {
  String value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Perform {}


@Person()
public class TestAnnotation {
  @Check(value = "hi")
  int a;

  @Perform
  public void testMethod() {
    System.out.println("我是 TestMethod");
  }

  public static void main(String[] args) {
    if (TestAnnotation.class.isAnnotationPresent(Person.class)) {
      Person person = TestAnnotation.class.getAnnotation(Person.class);
      if (person != null) {
        System.out.println(person.id());
        System.out.println(person.role());
      }
    }

    try {
      Field a = TestAnnotation.class.getDeclaredField("a");
      a.setAccessible(true);
      Check check = a.getAnnotation(Check.class);
      if (check != null) {
        System.out.println("check value : " + check.value());
      }

      Method method = TestAnnotation.class.getDeclaredMethod("testMethod");
      method.setAccessible(true);
      Object object = TestAnnotation.class.newInstance();
      method.invoke(object);

      Perform perform = method.getAnnotation(Perform.class);
      if (perform != null) {
        System.out.println(perform.annotationType());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
