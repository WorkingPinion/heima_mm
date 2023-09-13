package com.itheima.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *  要做的事：
 *      1 加载bean.properties属性文件
 *      2 读取配置文件内容
 *      3 反射创建XxxServiceImpl对象
 *      4 存储在map集合中
 */
public class BeanFactory {
  //定义存储service对象的map集合
  public static final Map<String,Object> cache=new HashMap<>();
  static {
    //1 加载bean.properties属性文件
    //1.1 创建properties对象
    Properties properties=new Properties();
    //1.2 加载配置文件
    InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
    try {
      properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    //1.3 获取所有的key并遍历
    Set<String> names = properties.stringPropertyNames();
    names.forEach(name->{
      //2 读取配置文件内容
      String className = properties.getProperty(name);
      //3 反射创建XxxServiceImpl对象
      Object bean = null;
      try {
        bean = Class.forName(className).newInstance();
      } catch (Exception e) {
        e.printStackTrace();
      }
      //4 存储在map集合中
      cache.put(name,bean);
    });
  }
  //定义方法，通过该方法获取service对象
  public static<T> T getBean(String name,Class<T> tClass){
    return (T) cache.get(name);
  }
}