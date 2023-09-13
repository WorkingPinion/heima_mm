package com.itheima.web.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.domain.QuestionItem;
import com.itheima.domain.Result;
import com.itheima.service.QuestionItemService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.BeanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 处理和企业相关的所有请求：添加、修改、删除、分页查询等都是访问该Servlet，所有的请求需要携带一个标记参数
 * 分页查询路径：/store/questionItem/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/store/questionItem/save
 * 修改数据：/store/questionItem/edit
 * 删除数据：/store/questionItem/delete
 */
@WebServlet("/store/questionItem/*")
public class QuestionItemServlet extends BaseServlet {
    //统一获取对象
    private QuestionItemService questionItemService = BeanFactory.getBean("questionItemService", QuestionItemService.class);

    /**
     * 查询数据
     *
     * @param request
     * @param response
     */
    private Result findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 获取问题id
            String questionId = request.getParameter("questionId");
            //2 调用service层方法获取分页结果
            List<QuestionItem> list = questionItemService.findAll(questionId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_QUESTIONITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTIONITEM_FAIL);
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
            //1 获取请求参数并封装成QuestionItem对象
            QuestionItem questionItem = BeanUtil.fillBeanFromJson(request, QuestionItem.class);
            //2 调用service层方法添加数据
           
            questionItemService.save(questionItem);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_QUESTIONITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_QUESTIONITEM_FAIL);
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
            String questionItemId = request.getParameter("id");
            //2 调用service层方法获取分页结果
           
            QuestionItem questionItem = questionItemService.findById(questionItemId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_QUESTIONITEM_SUCCESS, questionItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTIONITEM_FAIL);
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
            QuestionItem questionItem = BeanUtil.fillBeanFromJson(request, QuestionItem.class);
            //2 调用service层方法，修改数据
           
            questionItemService.update(questionItem);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_QUESTIONITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_QUESTIONITEM_FAIL);
        }
    }

    /**
     * 处理删除数据的方法
     * @param request
     * @param response
     */
    private Result delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1 获取请求参数
            String id = request.getParameter("id");
            //2 调用service层方法，删除数据
            questionItemService.delete(id);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_QUESTIONITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_QUESTIONITEM_FAIL);
        }
    }
}

