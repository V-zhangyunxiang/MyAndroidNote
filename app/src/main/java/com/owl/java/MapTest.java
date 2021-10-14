package com.owl.java;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapTest {
  /* Map:是一个单独的接口，不属于 Collection,属于集合,一对一对的存,称为键值对.
   * HashMap:底层使用的数据是哈希表，线程不安全的
   * TreeMap:底层使用的数据是二叉树，线程不安全的
   *
   * HashMap 保证键不重复,不重复的原因跟 HashSet 一样
   * TreeMap 的排序方式, 去重与 TreeSet 一样
   */
  @RequiresApi(api = VERSION_CODES.N)
  public static void main(String[] args) {
    // 键是不能重复的，如果重复 后添加的会覆盖之前的
    HashMap<String, String> map = new HashMap<>();
    map.put("001", "haha");
    map.put("002", "jj");
    map.put("003", "ll");
    map.put("001", "xii"); // 返回上一次相同键的值
    map.remove("001"); // 根据键删除一对
    System.out.println(map);

    String value = map.get("002"); // 根据键获取值
    System.out.println(value);

    System.out.println(map.values()); // 得到所有值
    map.replace("001", "mm"); // 替换
    System.out.println(map.values());

    boolean b = map.containsKey("001");
    boolean a = map.containsValue("jj");
    System.out.println(b + "-" + a);

    // 遍历Map集合

    // 第一种方法 KeySet() 先拿到键的set集合，再通过遍历键拿到值
    //	   Set<String> keys= map.keySet();
    //	   Iterator<String> ite= keys.iterator();
    //	    while(ite.hasNext()){
    //	    	String key=ite.next();
    //	    	String va=map.get(key);
    //	    	System.out.println(key+"-"+va);
    //	    }

    // 第二种方法 entrySet() 得到每个键值对 对应的映射关系类型的值，存到 set 集合
    // 并返回该集合，从这个映射关系类型值中即可以得到键，也可以得到值
    Set<Map.Entry<String, String>> entry = map.entrySet();
    Iterator<Map.Entry<String, String>> ite2 = entry.iterator();
    while (ite2.hasNext()) {
      Map.Entry<String, String> en = ite2.next();
      String k = en.getKey();
      String value2 = en.getValue();
      System.out.println(k + "-" + value2);
    }
    // Entry 是定义在 Map 中的一个静态接口
    // 有了集合，有了集合中的键值对，才会存在映射关系
    // 所以映射是对集合内部事物的描述,所以定义在Map内部
  }
}
