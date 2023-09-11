package com.itheima.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    /**
     * service是Servlet的生命周期方法，每次访问Servlet都会执行。
     * 问题：System.out.println(this);打印的是com.itheima.web.controller.CompanyServlet@47b24e46，为什么？
     * 解决：方法中的this表示，哪个对象调用该方法，this就表示哪个对象。当我们客户端发起请求访问CompanyServlet时，tomcat服务器会创建CompanyServlet对象，创建完成之后会调用init方法初始化，接着调用service方法，也就是companyServlet.service(request,response)，此时我们的companyServlet没有重写这个方法，那么就会使用父类BaseServlet中定义的service方法，所有此时父类BaseServlet的service方法中打印的this表示的是子类对象。
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1 获取请求标记
            //获取Servlet一级目录后面的路径 ，也就是/list、/save、/edit、/delete 等
            String pathInfo = request.getPathInfo().substring(1);
            //2 获取对应方法Method对象,this表示子类对象
            Method method = this.getClass().getDeclaredMethod(pathInfo, HttpServletRequest.class, HttpServletResponse.class);
            //3 开启访问权限，暴力访问
            method.setAccessible(true);
            //4 反射执行方法
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}