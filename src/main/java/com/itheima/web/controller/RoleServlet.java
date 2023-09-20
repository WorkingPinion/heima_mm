package com.itheima.web.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Result;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import com.itheima.service.ModuleService;
import com.itheima.service.RoleService;
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
 * 分页查询路径：/system/role/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/system/role/save
 * 修改数据：/system/role/edit
 * 删除数据：/system/role/delete
 */
@WebServlet("/system/role/*")
public class RoleServlet extends BaseServlet {
    //统一获取对象
    private RoleService roleService = BeanFactory.getBean("roleService", RoleService.class);
    private ModuleService moduleService = BeanFactory.getBean("moduleService", ModuleService.class);

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
            PageInfo<Role> info = roleService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
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
            //1 获取请求参数并封装成Role对象
            Role role = BeanUtil.fillBeanFromJson(request, Role.class);
            //2 调用service层方法添加数据
           
            roleService.save(role);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_ROLE_FAIL);
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
            String roleId = request.getParameter("id");
            //2 调用service层方法获取分页结果
           
            Role role = roleService.findById(roleId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
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
            Role role = BeanUtil.fillBeanFromJson(request, Role.class);
            //2 调用service层方法，修改数据
           
            roleService.update(role);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
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
           
            roleService.delete(ids);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }
    /**
     * 查询所有企业后台代码
     *
     * @param request
     * @param response
     */
    private Result findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 调用service层方法获取分页结果
            List<Role> list = roleService.findAll();
            //2 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    /**
     * 更新角色绑定的模块
     * @param request
     * @param response
     */
    private Result updateRoleAndModules(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 获取请求参数
            String roleId = request.getParameter("roleId");
            String[] moduleIds = BeanUtil.fillBeanFromJson(request, String[].class);
            //2 调用service层方法，查询所有数据
            roleService.updateRoleAndModules(roleId,moduleIds);
            //更新角色绑定的模块时更新权限保存在session中
            Object user = request.getSession().getAttribute("user");
            if(user!=null){
                User userOne= (User) user;
                List<String> curls = moduleService.findPermissionByUserId(userOne.getId());
                request.getSession().setAttribute("curls", curls);
                System.out.println( request.getSession().getAttribute("curls"));
            }

            //3 响应结果
            return new Result(true, MessageConstant.SETTING_ROLE_AUTHORIZE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SETTING_ROLE_AUTHORIZE_FAIL);
        }
    }

    /**
     * 查询用户绑定的角色id们
     * @param request
     * @param response
     */
    private Result findRoleIdsByUserId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1 获取请求参数
            String userId = request.getParameter("userId");
            //2 调用service层方法查询用户绑定的角色id们
            String[] checkedRoleIds = roleService.findRoleIdsByUserId(userId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,checkedRoleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }
}

