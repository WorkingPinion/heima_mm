package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.QuestionDao;
import com.itheima.domain.Question;
import com.itheima.service.QuestionService;
import com.itheima.utils.MybatisUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.File;
import java.util.List;
import java.util.UUID;
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
                    File file = new File(uploadPath,question.getPicture());
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
}