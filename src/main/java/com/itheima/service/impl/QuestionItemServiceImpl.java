package com.itheima.service.impl;

import com.itheima.dao.QuestionItemDao;
import com.itheima.domain.QuestionItem;
import com.itheima.service.QuestionItemService;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;

/**
 * @author xz
 */
public class QuestionItemServiceImpl implements QuestionItemService {
    @Override
    public List<QuestionItem> findAll(String questionId) {
        SqlSession sqlSession = null;
        List<QuestionItem> list=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionItemDao questionItemDao = MybatisUtil.getMapper(sqlSession, QuestionItemDao.class);
            //3 执行操作释放资源
            list=questionItemDao.findAll(questionId);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }

    @Override
    public void save(QuestionItem questionItem) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionItemDao questionItemDao = MybatisUtil.getMapper(sqlSession, QuestionItemDao.class);

            //生成一个唯一的id，保存到questionItem
            String id = UUID.randomUUID().toString().replace("-","");
            questionItem.setId(id);

            //3 执行操作
            questionItemDao.save(questionItem);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public QuestionItem findById(String id) {
        SqlSession sqlSession = null;
        QuestionItem questionItem=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionItemDao questionItemDao = MybatisUtil.getMapper(sqlSession, QuestionItemDao.class);
            //3 执行操作释放资源
            questionItem= questionItemDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return questionItem;
    }
    @Override
    public void update(QuestionItem questionItem) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionItemDao questionItemDao = MybatisUtil.getMapper(sqlSession, QuestionItemDao.class);
            //3 执行操作
            questionItemDao.update(questionItem);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }
    @Override
    public void delete(String id) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            QuestionItemDao questionItemDao = MybatisUtil.getMapper(sqlSession, QuestionItemDao.class);
            //3 执行操作,dao中的方法不用修改
            questionItemDao.delete(id);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }
}