package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.QuestionDao;
import com.itheima.domain.Question;
import com.itheima.service.QuestionService;
import com.itheima.utils.MybatisUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class QuestionServiceImpl implements QuestionService {
    @Override
    public PageInfo<Question> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Question> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionDao questionDao = MybatisUtil.getMapper(sqlSession, QuestionDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Question> list = questionDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Question question) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionDao questionDao = MybatisUtil.getMapper(sqlSession, QuestionDao.class);

            //生成一个唯一的id，保存到question
            String id = UUID.randomUUID().toString().replace("-", "");
            question.setId(id);

            //3 执行操作
            questionDao.save(question);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Question findById(String id) {
        SqlSession sqlSession = null;
        Question question = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionDao questionDao = MybatisUtil.getMapper(sqlSession, QuestionDao.class);
            //3 执行操作释放资源
            question = questionDao.findById(id);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return question;
    }

    @Override
    public void update(Question question) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionDao questionDao = MybatisUtil.getMapper(sqlSession, QuestionDao.class);
            //3 执行操作
            questionDao.update(question);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public void delete(String[] ids, String uploadPath) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionDao questionDao = MybatisUtil.getMapper(sqlSession, QuestionDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id ->
            {
                Question question = questionDao.findById(id);
                questionDao.delete(id);
                if (!StringUtils.isNullOrEmpty(question.getPicture())) {
                    //删除图片
                    File file = new File(uploadPath, question.getPicture());
                    file.delete();
                }

            });
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public void toExport(OutputStream outputStream) throws IOException, InvalidFormatException {
        //1 获取模版文件的位置
        String path = this.getClass().getClassLoader().getResource("question_template.xlsx").getPath();
        System.out.println("path = " + path);
        //2 创建工作薄读取模版excel文件
        XSSFWorkbook workbook = new XSSFWorkbook(new File(path));
        //3 获取工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //4 调用findAll方法，获取所有的问题集合
        List<Question> questionList = findByPage(0, 0).getList();
        //5 转换集合，数组中存储的是每一道题的属性值
        List<String[]> questions = questionList.stream()
                .map(question -> {
                            String[] fields = new String[12];
                            fields[0] = question.getId();
                            fields[1] = question.getCompanyId();
                            fields[2] = question.getCatalogId();
                            fields[3] = question.getRemark();
                            fields[4] = question.getSubject();
                            fields[5] = question.getPicture();
                            fields[6] = question.getAnalysis();
                            fields[7] = question.getType();
                            fields[8] = question.getDifficulty();
                            fields[9] = question.getIsClassic();
                            fields[10] = question.getState();
                            fields[11] = question.getReviewStatus();
                            return fields;

                        }

                )
                .collect(Collectors.toList());
        //6 遍历所有题目，创建row和cell，向cell中写数据
        //定义通用的样式
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        //开始写数据
        int rowIndex = 3; //从模版第三行开始
        for (String[] fields : questions) {
            //每一个问题创建一行
            XSSFRow row = sheet.createRow(rowIndex++);
            int cellIndex = 1; //从模版第一列开始
            for (String field : fields) {
                //每一个属性创建一列
                XSSFCell cell = row.createCell(cellIndex++);
                //向单元格中写入数据
                cell.setCellValue(field);
                //设置样式
                cell.setCellStyle(style);
            }
        }
        //7 将workbook中的内容输出
        workbook.write(outputStream);
        workbook.close();
    }
}