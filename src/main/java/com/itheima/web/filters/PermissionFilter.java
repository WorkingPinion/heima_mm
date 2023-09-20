package com.itheima.web.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Result;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户的操作权限验证过滤器
 */
@WebFilter(urlPatterns = {"/system/*","/store/*"})
public class PermissionFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    //1 获取访问路径
    String servletPath = request.getServletPath(); // 得到/system/dept 或者/system/user ...
    String pathInfo = request.getPathInfo(); //得到标记 /findByPage /findAll /save /edit ...
    //2 如果标记是查询类的，我们直接放行，不进行权限验证
    if (pathInfo.substring(1).startsWith("find") || pathInfo.substring(1).startsWith("select")) {
      chain.doFilter(req, resp);
      return;
    }
    //3 不是查询类的方法就要进行权限验证了
    //3.1 获取用户的权限列表
    List<String> curls = (List<String>) request.getSession().getAttribute("curls");
    chain.doFilter(req, resp);
    //3.2 如果用户的权限列表不是空,就进行权限校验
    if (curls != null && curls.contains(servletPath + pathInfo)) {
      //校验成功
      chain.doFilter(req, resp);
    } else {
      //响应json
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(response.getOutputStream(), new Result(false, MessageConstant.PERMISSION_FORBIDDEN));
    }
  }

  @Override
  public void destroy() {

  }
}