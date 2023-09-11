package com.itheima.test;

import com.itheima.domain.Question;
import com.itheima.utils.MD5Util;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class POITest {

    //需求：向excel中写数据
    @Test
    public void test1() throws IOException {
        //1 创建一个工作薄对象，相当于在内存中创建了一个excel文件
        Workbook workbook = new XSSFWorkbook();
        //2 在工作簿中创建工作表，也就是sheet
        Sheet sheet = workbook.createSheet("传智教育");
        //3 在工作表中创建行，也就是row
        Row row = sheet.createRow(1);
        //4 在行中创建列，也叫单元格，也就是cell
        Cell cell = row.createCell(2);
        //5 在单元格中写入数据
        cell.setCellValue("黑马程序员-胡继周-胡花粥");

        //需求：给单元格加边框
        //1 通过工作簿创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //2 定义样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.DOUBLE);
        cellStyle.setBorderTop(BorderStyle.DASHED);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //3 将样式设置给cell
        cell.setCellStyle(cellStyle);
        //需求：合并单元格
        sheet.addMergedRegion(new CellRangeAddress(1,3,2,4));


        //6 将工作簿写到硬盘中，释放资源
        FileOutputStream fos=new FileOutputStream("test.xlsx");
        workbook.write(fos);
        //释放资源
        fos.close();
        workbook.close();
    }

    @Test
    public void test2() throws IOException {
        //1 创建一个工作薄对象并且读取硬盘中的excel，相当于在内存中创建了一个excel文件
        Workbook workbook=new XSSFWorkbook("test.xlsx");
        //2 在工作簿中获取工作表，也就是sheet
        Sheet sheet = workbook.getSheetAt(0);
        //3 在工作表中获取行，也就是row
        Row row = sheet.getRow(1);
        //4 在行中获取列，也叫单元格，也就是cell
        Cell cell = row.getCell(2);
        //5 在单元格中获取数据
        String value = cell.getStringCellValue();
        System.out.println("value = " + value);
        //6 释放资源
        workbook.close();
    }

    @Test
    public void test3(){
        //需求：List<Question>转换成List<String[]> ，String[]中保存的是Question的各个属性值
        //1 创建Question对象
        Question question=new Question();
        question.setId("100");
        question.setCompanyId("1");
        question.setRemark("springmvc的执行流程");
        question.setSubject("springmvc的执行流程");
        question.setPicture("xxx.jpg");

        //2 创建List<Question>集合，将Question对象保存到集合中
        List<Question> questionList=new ArrayList<>();
        questionList.add(question);

        //3 List<Question>转换成List<String[]>String[]中保存的是Question的各个属性值
        /*List<String[]> newList=new ArrayList<>();
        for (Question q : questionList) {
            String[] arr=new String[5];
            arr[0]=q.getId();
            arr[1]=q.getCompanyId();
            arr[2]=q.getRemark();
            arr[3]=q.getSubject();
            arr[4]=q.getPicture();
            newList.add(arr);
        }*/
        List<String[]> newList = questionList.stream().map(q -> {
            String[] arr = new String[5];
            arr[0] = q.getId();
            arr[1] = q.getCompanyId();
            arr[2] = q.getRemark();
            arr[3] = q.getSubject();
            arr[4] = q.getPicture();
            return arr;

        }).collect(Collectors.toList());
    }

    @Test
    public void md5(){
        //像这样复杂的密码，在线解密工具也解不出来
        String s = MD5Util.md5("江南128qHjkfaf");
        System.out.println("s = " + s);
    }
}
