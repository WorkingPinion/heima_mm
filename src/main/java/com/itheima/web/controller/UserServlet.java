package com.itheima.web.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Result;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 处理和企业相关的所有请求：添加、修改、删除、分页查询等都是访问该Servlet，所有的请求需要携带一个标记参数
 * 分页查询路径：/store/User/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/store/User/save
 * 修改数据：/store/User/edit
 * 删除数据：/store/User/delete
 */
@WebServlet("/system/user/*")
public class UserServlet extends BaseServlet {
    //统一获取对象
    private UserService userService = BeanFactory.getBean("userService", UserService.class);

    /**
     * 处理分页查询的请求
     *
     * @param request
     * @param response
     */
    private Result findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1 获取请求参数（当前页数和每页条数）
            String _currentPage = request.getParameter("currentPage");
            String _pageSize = request.getParameter("pageSize");
            //判断非空
            int currentPage = 1, pageSize = 5; //定义默认的页码和每页条数
            if (!StringUtils.isBlank(_currentPage)) {
                currentPage = Integer.parseInt(_currentPage);
            }
            if (!StringUtils.isBlank(_pageSize)) {
                pageSize = Integer.parseInt(_pageSize);
            }
            //2 调用service层方法获取分页结果
            UserService UserService = new UserServiceImpl();
            PageInfo<User> info = UserService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    /**
     * 处理添加数据的请求
     *
     * @param request
     * @param response
     */
    private Result save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1 获取请求参数并封装成User对象
            User User = BeanUtil.fillBeanFromJson(request, User.class);
            //2 调用service层方法添加数据
            UserService UserService = new UserServiceImpl();
            UserService.save(User);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_USER_FAIL);
        }
    }

    /**
     * 处理根据id查询的请求
     *
     * @param request
     * @param response
     */
    private Result findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1 获取请求参数
            String UserId = request.getParameter("id");
            //2 调用service层方法获取分页结果
            UserService UserService = new UserServiceImpl();
            User User = UserService.findById(UserId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, User);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    /**
     * 处理修改数据的请求
     *
     * @param request
     * @param response
     */
    private Result edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1 获取请求参数并封装
            User User = BeanUtil.fillBeanFromJson(request, User.class);
            //2 调用service层方法，修改数据
            userService.update(User);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
    }

    /**
     * 处理删除数据的方法
     *
     * @param request
     * @param response
     */
    private Result delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1 获取请求参数,虽然只有一个id，也要封装成对象
            String[] ids = BeanUtil.fillBeanFromJson(request, String[].class);
            //2 调用service层方法，删除数据
            UserService UserService = new UserServiceImpl();
            UserService.delete(ids);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }


    /**
     * 处理查询所有数据的请求
     *
     * @param request
     * @param response
     */
    private Result findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            //2 调用service层方法，查询所有数据
            UserService userService = new UserServiceImpl();
            List<User> list = userService.findAll();
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }

    /**
     * 根据用户id跟新角色，也叫给用户授权
     * @param request
     * @param response
     */
    private Result updateUserAndRoles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 获取请求参数 userId 和checkedRoleIds
            String userId = request.getParameter("userId");
            String[] checkedRoleIds = BeanUtil.fillBeanFromJson(request, String[].class);
            //2 调用service，根据用户绑定的角色也叫给用户授权
            userService.updateUserAndRoles(userId,checkedRoleIds);
            //3 响应结果到客户端
            return new Result(true,MessageConstant.SETTING_USER_AUTHORIZE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SETTING_USER_AUTHORIZE_FAIL);
        }
    }

    /**
     * 查询登录的用户信息
     */
    private Result findLoginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1 获取session域中的用户对象
            Object user = request.getSession().getAttribute("user");
            //2 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }
}

