package com.itheima.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.User;
import com.itheima.domain.Result;
import com.itheima.service.UserService;
import com.itheima.service.UserServiceImpl;
import com.itheima.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 获取请求标记
        //获取Servlet一级目录后面的路径 ，也就是/list、/save、/edit、/delete 等
        String pathInfo = request.getPathInfo().substring(1);
        //2 判断标记，调用不同的方法处理请求
        if ("findByPage".equals(pathInfo)) {
            //调用分页查询的方法
            findByPage(request, response);
        } else if ("save".equals(pathInfo)) {
            save(request, response);
        } else if ("findById".equals(pathInfo)) {
            findById(request, response);
        } else if ("edit".equals(pathInfo)) {
            edit(request, response);
        } else if ("delete".equals(pathInfo)) {
            delete(request, response);
        } else if ("findAll".equals(pathInfo)) {
            findAll(request, response);
        }
    }

    /**
     * 处理分页查询的请求
     *
     * @param request
     * @param response
     */
    private void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
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
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.QUERY_DEPT_SUCCESS, info));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.QUERY_DEPT_FAIL));
        }
    }

    /**
     * 处理添加数据的请求
     *
     * @param request
     * @param response
     */
    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //1 获取请求参数并封装成User对象
            User User = BeanUtil.fillBeanFromJson(request, User.class);
            //2 调用service层方法添加数据
            UserService UserService = new UserServiceImpl();
            UserService.save(User);
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.SAVE_DEPT_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.SAVE_DEPT_FAIL));
        }
    }

    /**
     * 处理根据id查询的请求
     *
     * @param request
     * @param response
     */
    private void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //1 获取请求参数
            String UserId = request.getParameter("id");
            //2 调用service层方法获取分页结果
            UserService UserService = new UserServiceImpl();
            User User = UserService.findById(UserId);
            //3 对客户的作出响应
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.QUERY_DEPT_SUCCESS, User));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.QUERY_DEPT_FAIL));
        }
    }

    /**
     * 处理修改数据的请求
     *
     * @param request
     * @param response
     */
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //1 获取请求参数并封装
            User User = BeanUtil.fillBeanFromJson(request, User.class);
            //2 调用service层方法，修改数据
            UserService UserService = new UserServiceImpl();
            UserService.update(User);
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.EDIT_DEPT_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.EDIT_DEPT_FAIL));
        }
    }

    /**
     * 处理删除数据的方法
     *
     * @param request
     * @param response
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //1 获取请求参数,虽然只有一个id，也要封装成对象
            String[] ids = BeanUtil.fillBeanFromJson(request, String[].class);
            //2 调用service层方法，删除数据
            UserService UserService = new UserServiceImpl();
            UserService.delete(ids);
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.DELETE_DEPT_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.DELETE_DEPT_FAIL));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 处理查询所有数据的请求
     * @param request
     * @param response
     */
    private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //2 调用service层方法，查询所有数据
            UserService userService = new UserServiceImpl();
            List<User> list = userService.findAll();
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.DELETE_DEPT_SUCCESS, list));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.DELETE_DEPT_FAIL));
        }
    }
}

