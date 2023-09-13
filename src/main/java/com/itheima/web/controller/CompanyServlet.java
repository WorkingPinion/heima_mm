package com.itheima.web.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Company;
import com.itheima.domain.Result;
import com.itheima.service.CompanyService;
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
 * 分页查询路径：/store/company/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/store/company/save
 * 修改数据：/store/company/edit
 * 删除数据：/store/company/delete
 */
@WebServlet("/store/company/*")
public class CompanyServlet extends BaseServlet {
    //统一获取对象
    private CompanyService companyService = BeanFactory.getBean("companyService", CompanyService.class);

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
            PageInfo<Company> info = companyService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_COMPANY_SUCCESS, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_COMPANY_FAIL);
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
            //1 获取请求参数并封装成Company对象
            Company company = BeanUtil.fillBeanFromJson(request, Company.class);
            //2 调用service层方法添加数据
           
            companyService.save(company);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_COMPANY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_COMPANY_FAIL);
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
            String companyId = request.getParameter("id");
            //2 调用service层方法获取分页结果
           
            Company company = companyService.findById(companyId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_COMPANY_SUCCESS, company);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_COMPANY_FAIL);
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
            Company company = BeanUtil.fillBeanFromJson(request, Company.class);
            //2 调用service层方法，修改数据
           
            companyService.update(company);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_COMPANY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_COMPANY_FAIL);
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
           
            companyService.delete(ids);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_COMPANY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_COMPANY_FAIL);
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
            List<Company> list = companyService.findAll();
            //2 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_COMPANY_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_COMPANY_FAIL);
        }
    }
}

