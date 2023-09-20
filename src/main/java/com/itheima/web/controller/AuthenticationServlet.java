package com.itheima.web.controller;

import com.itheima.domain.User;
import com.itheima.service.ModuleService;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.mysql.jdbc.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/authentication/*")
public class AuthenticationServlet extends BaseServlet {
    //获取service对象
    private UserService userService = BeanFactory.getBean("userService", UserService.class);
    private ModuleService moduleService = BeanFactory.getBean("moduleService", ModuleService.class);

    /**
     * 处理用户登录的请求
     *
     * @return
     */
    private void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1 获取请求参数邮箱和密码
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            //2 调用service，获取用户信息
            User user = userService.login(email, password);
            //3 判断是否登录成功
            if (user != null) {
                //4 如果登录成功，保存用户信息到session中，响应结果
                request.getSession().setAttribute("user", user);
                //用户登录时查询权限保存在session中
                List<String> curls = moduleService.findPermissionByUserId(user.getId());
                request.getSession().setAttribute("curls", curls);
                //5获取被拦截的路径
                String sendRedirect = (String) request.getSession().getAttribute("sendRedirect");
                if (!StringUtils.isNullOrEmpty(sendRedirect)) {
                    response.sendRedirect(sendRedirect);
                } else {
                    response.sendRedirect(request.getContextPath() + "/pages/main.html");
                }

            } else {
                //6 如果登录失败，响应失败的结果
                response.sendRedirect(request.getContextPath() + "/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理用户退出的请求
     *
     * @return
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session
        request.getSession().invalidate();
        //重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}