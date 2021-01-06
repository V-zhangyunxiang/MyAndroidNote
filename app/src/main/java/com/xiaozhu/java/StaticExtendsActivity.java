package com.xiaozhu.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/9/28 14:52
 */
public class StaticExtendsActivity {

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<Integer>();
    list.add(1);
    System.out.println(list);
    list.add(2);

    List list1 = Collections.unmodifiableList(list);
    System.out.println(list1);

    //        HashMap<String,Integer> list = new HashMap();
    //        list.put("5",5);
    //        list.put("2",5);
    //        list.put("3",5);
    //        list.put("4",5);
    //
    //        	   Set<String> keys= list.keySet();
    //        	   Iterator<String> ite= keys.iterator();
    //        	    while(ite.hasNext()){
    //        	    	String key=ite.next();
    //        	    	int va=list.get(key);
    //        	    	System.out.println(key+"-"+va);
    //        	    }

    //        List<String> list = new ArrayList<String>();
    //        list.add("a");
    //        list.add("b");
    //        list.add("c");
    //        list.add("d");
    //        list.add("e");
    //        list.add(2, "f");
    //        System.out.println(list);
    //                System.out.println(B.a);
    //                System.out.println(B.b);
    //                B.printB();
    //                B.printA();
    //        String ip = "192. 168.128. 33";
    //        StringTokenizer token=new StringTokenizer(ip,".");
    //        while(token.hasMoreElements()){
    //            System.out.print(token.nextToken()+"  ");
    //        }
    //        String str = "<img
    // src=\"image.xiaozhustatic2.com/00,1498,1000/18,0,61,20425,1498,1000,8deea8cb.png\"/>";
    //        // 这里我们考虑了一些不规范的 img 标签写法，比如：空格、引号
    //        Pattern pattern =
    // Pattern.compile("<img\\s+src=(?:['\"])?(?<src>\\w+.(jpg|png))(?:['\"])?\\s*/>");
    //        Matcher matcher = pattern.matcher(str);
    //        while (matcher.find()) {
    //            System.out.println(matcher.group("src"));
    //        }
    //    }
    //        C c = new D();
    //        System.out.println(c.a);
    //        System.out.println(c.c);
    //        c.print();

    //        D d = new D();
    //        System.out.println(d.a);
    //        d.print();
    //        d.ss();
    //        System.out.println(3 | 9);

    //        String str = "<div>文章标题</div><div>发布时间</div>";
    //        // 贪婪模式
    //        Pattern pattern = Pattern.compile("<div>(?<title>.+)</div>");
    //        Matcher matcher = pattern.matcher(str);
    //        while (matcher.find()) {
    //            System.out.println(matcher.group("title"));
    //        }
    //
    //        String text = "<img
    // src=\"https://image.xiaozhustatic2.com/00,1498,1000/18,0,61,20425,1498,1000,8deea8cb.jpg\"
    // title=\"\" alt=\"\"/><img
    // src=\"https://image.xiaozhustatic2.com/00,600,480/18,0,56,20195,600,480,a5d3dc9b.jpg\"
    // title=\"\" alt=\"\"/><img
    // src=\"https://image.xiaozhustatic2.com/00,600,480/18,0,99,19932,600,480,4053330c.jpg\"
    // title=\"\" alt=\"\"/><img
    // src=\"https://image.xiaozhustatic1.com/00,480,600/18,0,84,20242,480,600,8a649103.jpg\"
    // title=\"\" alt=\"\"/>";
    //        Pattern pattern =
    // Pattern.compile("<img\\s+src=[\"|\']?([\\w\\W]*?)(jpg|png)[\\w\\W]*?/>");
    //        Matcher matcher = pattern.matcher(text);
    //        // 遍例所有匹配的序列
    //        while (matcher.find()) {
    //            System.out.println(matcher.group(1) + matcher.group(2));
    //        }

  }
}

// class A {
//    static int a = 1;
//    static int b = 2;
//
//    public static void printA() {
//        System.out.println(a);
//    }
//
//    public static void printB() {
//        System.out.println(b);
//    }
// }
//
//
// class B extends A {
//    static int a = 3;
//    static int b = 4;
//
//    public static void printB() {
//        System.out.println(b);
//    }
//
//    public static void printA() {
////        System.out.println(a);
////    }
//
// }

class C {
  int a = 2;
  static int c = 4;

  void print() {
    System.out.print("CCC");
  }

  static void ss() {
    System.out.print("SC");
  }
}

class D extends C {
  int a = 3;
  static int c = 5;

  void print() {
    System.out.print("DDD");
  }

  static void ss() {
    System.out.print("SD");
  }
}
