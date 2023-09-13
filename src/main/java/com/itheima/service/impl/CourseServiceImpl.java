package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.CourseDao;
import com.itheima.domain.Course;
import com.itheima.service.CourseService;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class CourseServiceImpl implements CourseService {
    @Override
    public PageInfo<Course> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Course> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CourseDao courseDao = MybatisUtil.getMapper(sqlSession, CourseDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Course> list = courseDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Course course) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CourseDao courseDao = MybatisUtil.getMapper(sqlSession, CourseDao.class);

            //生成一个唯一的id，保存到course
            String id = UUID.randomUUID().toString().replace("-","");
            course.setId(id);

            //3 执行操作
            courseDao.save(course);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Course findById(String id) {
        SqlSession sqlSession = null;
        Course course=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CourseDao courseDao = MybatisUtil.getMapper(sqlSession, CourseDao.class);
            //3 执行操作释放资源
            course= courseDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return course;
    }
    @Override
    public void update(Course course) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CourseDao courseDao = MybatisUtil.getMapper(sqlSession, CourseDao.class);
            //3 执行操作
            courseDao.update(course);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }
    @Override
    public void delete(String[] ids) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CourseDao courseDao = MybatisUtil.getMapper(sqlSession, CourseDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id->courseDao.delete(id));
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public List<Course> findAll() {
        SqlSession sqlSession = null;
        List<Course> list=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CourseDao courseDao = MybatisUtil.getMapper(sqlSession, CourseDao.class);
            //3 执行操作释放资源
            list=courseDao.findAll();
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }
}