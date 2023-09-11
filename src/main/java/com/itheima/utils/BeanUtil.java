package com.itheima.utils;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * 填充表单数据到javabean的工具类
 * @author zhy
 *
 */
public final class BeanUtil {
	private BeanUtil(){}
	/**
	 * 封装请求参数到javabean中,请求参数的格式为name=value&name=value
	 * @param request	表单中的数据
	 * @param clazz		封装到哪个javabean
	 * @return	封装好的javabean对象
	 * 使用的是泛型。泛型必须先声明再使用。声明必须在返回值之前
	 * T指的就是泛型，它可以是任意字符，只是作为一个占位符。
	 * 声明时用什么字符，使用时就得用什么
	 */
	public static <T> T fillBean(HttpServletRequest request,Class<T> clazz){
		//1.定义一个T类型的javabean
		T bean = null;
		try{
			//2.实例化bean对象
			bean = clazz.newInstance();
			//3.使用BeanUtils的方法进行封装
			BeanUtils.populate(bean, request.getParameterMap());
			//4.返回
			return bean;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 封装请求参数到javabean中,请求参数的格式为json格式
	 * @param request	表单中的数据
	 * @param clazz		封装到哪个javabean
	 * @return	封装好的javabean对象
	 * 使用的是泛型。泛型必须先声明再使用。声明必须在返回值之前
	 * T指的就是泛型，它可以是任意字符，只是作为一个占位符。
	 * 声明时用什么字符，使用时就得用什么
	 */
	public static <T> T fillBeanFromJson(HttpServletRequest request,Class<T> clazz){
		T bean = null;
		try {
			//1.创建ObjectMapper对象
			ObjectMapper objectMapper=new ObjectMapper();
			//2 将request请求体中的json转换成javabean对象
			bean = objectMapper.readValue(request.getReader(), clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
