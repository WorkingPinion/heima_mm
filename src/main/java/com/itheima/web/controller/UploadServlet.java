package com.itheima.web.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //【第一步】判断是否请求是否支持文件上传
        if (ServletFileUpload.isMultipartContent(request)) {
            //【第二步】创建磁盘工厂对象，用于将页面上传的文件保存到硬盘中。
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //【第三步】创建ServletFileUpload文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //【第四步】解析请求对象，获取表单项集合
            try {
                List<FileItem> fileItems = fileUpload.parseRequest(request);
                //【第五步】遍历集合，判断是普通表单项还是文件上传项，如果是文件上传项就将文件写到服务器硬盘中。
                fileItems.forEach(fileItem -> {
                    if (fileItem.isFormField()) {
                        //是普通表单项,获取表单内容输出
                        String fieldName = fileItem.getFieldName(); //input表单的name属性值
                        try {
                            String value = fileItem.getString("utf-8");//输入框中的内容，字符集解决中文乱码问题
                            System.out.println(fieldName + "=" + value);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //是文件上传项,将文件写到服务器硬盘中。
                        //1 获取文件名
                        String filename = fileItem.getName();
                        //2 定义上传的文件保存的目录
                        String realPath = getServletContext().getRealPath("/upload");
                        //3 将文件写到硬盘upload目录中
                        try {
                            fileItem.write(new File(realPath, filename));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }

    }
}
