package com.itheima.web.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Catalog;
import com.itheima.domain.Result;
import com.itheima.service.CatalogService;
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
 * 分页查询路径：/store/catalog/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/store/catalog/save
 * 修改数据：/store/catalog/edit
 * 删除数据：/store/catalog/delete
 */
@WebServlet("/store/catalog/*")
public class CatalogServlet extends BaseServlet {
    //统一获取对象
    private CatalogService catalogService = BeanFactory.getBean("catalogService", CatalogService.class);

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
            PageInfo<Catalog> info = catalogService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_CATALOG_SUCCESS, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CATALOG_FAIL);
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
            //1 获取请求参数并封装成Catalog对象
            Catalog catalog = BeanUtil.fillBeanFromJson(request, Catalog.class);
            //2 调用service层方法添加数据
           
            catalogService.save(catalog);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_CATALOG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_CATALOG_FAIL);
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
            String catalogId = request.getParameter("id");
            //2 调用service层方法获取分页结果
           
            Catalog catalog = catalogService.findById(catalogId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_CATALOG_SUCCESS, catalog);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CATALOG_FAIL);
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
            Catalog catalog = BeanUtil.fillBeanFromJson(request, Catalog.class);
            //2 调用service层方法，修改数据
           
            catalogService.update(catalog);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_CATALOG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CATALOG_FAIL);
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
           
            catalogService.delete(ids);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_CATALOG_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CATALOG_FAIL);
        }
    }

    private Result findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 调用service层方法获取分页结果
            List<Catalog> list = catalogService.findAll();
            //2 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_CATALOG_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CATALOG_FAIL);
        }
    }
}

