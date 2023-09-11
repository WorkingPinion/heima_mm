package com.itheima.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Company;
import com.itheima.domain.Result;
import com.itheima.service.CompanyService;
import com.itheima.service.CompanyServiceImpl;
import com.itheima.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 处理和企业相关的所有请求：添加、修改、删除、分页查询等都是访问该Servlet，所有的请求需要携带一个标记参数
 * 分页查询路径：/store/company/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/store/company/save
 * 修改数据：/store/company/edit
 * 删除数据：/store/company/delete
 */
@WebServlet("/store/company/*")
public class CompanyServlet extends HttpServlet {
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
            CompanyService companyService = new CompanyServiceImpl();
            PageInfo<Company> info = companyService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.QUERY_COMPANY_SUCCESS, info));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.QUERY_COMPANY_FAIL));
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
            //1 获取请求参数并封装成Company对象
            Company company = BeanUtil.fillBeanFromJson(request, Company.class);
            //2 调用service层方法添加数据
            CompanyService companyService = new CompanyServiceImpl();
            companyService.save(company);
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.SAVE_COMPANY_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.SAVE_COMPANY_FAIL));
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
            String companyId = request.getParameter("id");
            //2 调用service层方法获取分页结果
            CompanyService companyService = new CompanyServiceImpl();
            Company company = companyService.findById(companyId);
            //3 对客户的作出响应
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.QUERY_COMPANY_SUCCESS, company));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.QUERY_COMPANY_FAIL));
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
            Company company = BeanUtil.fillBeanFromJson(request, Company.class);
            //2 调用service层方法，修改数据
            CompanyService companyService = new CompanyServiceImpl();
            companyService.update(company);
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.EDIT_COMPANY_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.EDIT_COMPANY_FAIL));
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
            CompanyService companyService = new CompanyServiceImpl();
            companyService.delete(ids);
            //3 响应结果
            objectMapper.writeValue(response.getWriter(), new Result(true, MessageConstant.DELETE_COMPANY_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            objectMapper.writeValue(response.getWriter(), new Result(false, MessageConstant.DELETE_COMPANY_FAIL));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

