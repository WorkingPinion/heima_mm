package com.itheima.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.DeptDao;
import com.itheima.domain.Dept;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class DeptServiceImpl implements DeptService {
    @Override
    public PageInfo<Dept> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Dept> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            DeptDao DeptDao = MybatisUtil.getMapper(sqlSession, DeptDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Dept> list = DeptDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Dept Dept) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            DeptDao DeptDao = MybatisUtil.getMapper(sqlSession, DeptDao.class);

            //生成一个唯一的id，保存到Dept
            String id = UUID.randomUUID().toString().replace("-","");
            Dept.setId(id);

            //3 执行操作
            DeptDao.save(Dept);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Dept findById(String id) {
        SqlSession sqlSession = null;
        Dept Dept=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            DeptDao DeptDao = MybatisUtil.getMapper(sqlSession, DeptDao.class);
            //3 执行操作释放资源
            Dept= DeptDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return Dept;
    }
    @Override
    public void update(Dept Dept) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            DeptDao DeptDao = MybatisUtil.getMapper(sqlSession, DeptDao.class);
            //3 执行操作
            DeptDao.update(Dept);
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
            DeptDao DeptDao = MybatisUtil.getMapper(sqlSession, DeptDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id->DeptDao.delete(id));
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public List<Dept> findAll() {
        SqlSession sqlSession = null;
        List<Dept> list=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            DeptDao deptDao = MybatisUtil.getMapper(sqlSession, DeptDao.class);
            //3 执行操作释放资源
            list=deptDao.findAll();
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }
}