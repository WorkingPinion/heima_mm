package com.itheima.web.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.constant.MessageConstant;
import com.itheima.domain.Question;
import com.itheima.domain.Result;
import com.itheima.service.QuestionService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.BeanUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * 处理和企业相关的所有请求：添加、修改、删除、分页查询等都是访问该Servlet，所有的请求需要携带一个标记参数
 * 分页查询路径：/store/question/findByPage，根据/findByPage调用findByPage方法处理分页查询的请求
 * 添加数据：/store/question/save
 * 修改数据：/store/question/edit
 * 删除数据：/store/question/delete
 */
@WebServlet("/store/question/*")
public class QuestionServlet extends BaseServlet {
    //统一获取对象
    private QuestionService questionService = BeanFactory.getBean("questionService", QuestionService.class);

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
            PageInfo<Question> info = questionService.findByPage(currentPage, pageSize);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_QUESTION_SUCCESS, info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTION_FAIL);
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
            //1 获取请求参数并封装成Question对象
            Question question = BeanUtil.fillBeanFromJson(request, Question.class);
            //2 调用service层方法添加数据

            questionService.save(question);
            //3 响应结果
            return new Result(true, MessageConstant.SAVE_QUESTION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SAVE_QUESTION_FAIL);
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
            String questionId = request.getParameter("id");
            //2 调用service层方法获取分页结果

            Question question = questionService.findById(questionId);
            //3 对客户的作出响应
            return new Result(true, MessageConstant.QUERY_QUESTION_SUCCESS, question);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTION_FAIL);
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
            Question question = BeanUtil.fillBeanFromJson(request, Question.class);
            //2 调用service层方法，修改数据

            questionService.update(question);
            //3 响应结果
            return new Result(true, MessageConstant.EDIT_QUESTION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_QUESTION_FAIL);
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
            // 获取upload文件夹的绝对路径
            String uploadPath = getServletContext().getRealPath("/upload");
            System.out.println("uploadPath = " + uploadPath);
            questionService.delete(ids,uploadPath);
            //3 响应结果
            return new Result(true, MessageConstant.DELETE_QUESTION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_QUESTION_FAIL);
        }
    }

    /**
     * 处理文件上传的请求
     *
     * @param request
     * @param response
     */
    private Result upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1 创建磁盘工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2 创建ServletFileUpload文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //3 解析请求，得到上传条目
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            //4 因为每次只会上传一张图片，不需要遍历
            FileItem fileItem = fileItems.get(0);
            //获取文件保存的位置
            String realPath = getServletContext().getRealPath("/upload");
            //原始文件名
            String filename = fileItem.getName();
            //需求：生成唯一不重复的文件名
            //生成唯一文件名
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //获取后缀名
            String suffix = filename.substring(filename.lastIndexOf("."));
            //组合新的文件名
            filename = uuid + suffix;
            //写到硬盘中
            fileItem.write(new File(realPath, filename));
            return new Result(true, MessageConstant.UPLOAD_QUESTIONIMG_SUCCESS, filename);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.UPLOAD_QUESTIONIMG_FAIL);
        }

    }

}