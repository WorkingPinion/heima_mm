package com.itheima.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录验证
 */
@WebFilter(urlPatterns = {"/pages/*","/system/*","/store/*"})
public class AuthenticationFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req=(HttpServletRequest) request;
    HttpServletResponse resp= (HttpServletResponse) response;
    //1 从session中获取用户信息
    Object user = req.getSession().getAttribute("user");
    //2 判断是否为null，如果为null就跳转到登录页面
    if(user==null){
      //将访问访问保存到session中，登录成功之后继续访问这个路径
      String requestURI = req.getRequestURI();
      req.getSession().setAttribute("sendRedirect",requestURI);

      //跳转到登录页面
      resp.sendRedirect(req.getContextPath()+"/login.html");
      return;
    }
    chain.doFilter(req,resp);
  }
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}
  @Override
  public void destroy() {}
}