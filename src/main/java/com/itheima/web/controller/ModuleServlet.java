package com.itheima.web.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Module;
import com.itheima.domain.Result;
import com.itheima.domain.User;
import com.itheima.service.ModuleService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 处理和企业相关的所有请求：添加、修改、删除、分页查询等都是访问该Servlet，所有的请求需要携带一个标记参数
 * 分页查询路径：/system/module/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/system/module/save
 * 修改数据：/system/module/edit
 * 删除数据：/system/module/delete
 */
@WebServlet("/system/module/*")
public class ModuleServlet extends BaseServlet {
    //统一获取对象
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
            PageInfo<Module> info = moduleService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_MODULE_SUCCESS, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MODULE_FAIL);
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
            //1 获取请求参数并封装成Module对象
            Module module = BeanUtil.fillBeanFromJson(request, Module.class);
            //2 调用service层方法添加数据

            moduleService.save(module);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_MODULE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_MODULE_FAIL);
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
            String moduleId = request.getParameter("id");
            //2 调用service层方法获取分页结果

            Module module = moduleService.findById(moduleId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_MODULE_SUCCESS, module);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MODULE_FAIL);
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
            Module module = BeanUtil.fillBeanFromJson(request, Module.class);
            //2 调用service层方法，修改数据

            moduleService.update(module);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_MODULE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MODULE_FAIL);
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

            moduleService.delete(ids);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_MODULE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MODULE_FAIL);
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
            List<Module> list = moduleService.findAll();
            //2 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_MODULE_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MODULE_FAIL);
        }
    }

    /**
     * 查询角色权限
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private Result findModulesByRoleId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 获取请求参数 id
            String roleId = request.getParameter("roleId");
            //2 调用service，根据id查询信息
            List<Map<String,Object>> list=moduleService.findModulesByRoleId(roleId);
            //3 响应结果到客户端
            return new Result(true,MessageConstant.LOAD_ZTREE_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.LOAD_ZTREE_FAIL);
        }
    }

    /**
     * 查询登录的用户拥有的菜单列表
     */
    private Result findMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1 获取session域中的用户对象
            User user = (User) request.getSession().getAttribute("user");
            //2 调用service，获取该用户具有的菜单列表
            List<Module> list=moduleService.findMenu(user.getId());
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }
    /**
     * 查询用户的权利列表
     */
    private Result findPermissionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取权限列表
            List<String> curls= (List<String>) request.getSession().getAttribute("curls");
            return new Result(true, MessageConstant.QUERY_PERMISSIONLIST_SUCCESS,curls);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSIONLIST_FAILE);
        }
    }
}

