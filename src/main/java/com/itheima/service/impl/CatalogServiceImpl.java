package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.CatalogDao;
import com.itheima.domain.Catalog;
import com.itheima.service.CatalogService;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class CatalogServiceImpl implements CatalogService {
    @Override
    public PageInfo<Catalog> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Catalog> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CatalogDao catalogDao = MybatisUtil.getMapper(sqlSession, CatalogDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Catalog> list = catalogDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Catalog catalog) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CatalogDao catalogDao = MybatisUtil.getMapper(sqlSession, CatalogDao.class);

            //生成一个唯一的id，保存到catalog
            String id = UUID.randomUUID().toString().replace("-","");
            catalog.setId(id);

            //3 执行操作
            catalogDao.save(catalog);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Catalog findById(String id) {
        SqlSession sqlSession = null;
        Catalog catalog=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CatalogDao catalogDao = MybatisUtil.getMapper(sqlSession, CatalogDao.class);
            //3 执行操作释放资源
            catalog= catalogDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return catalog;
    }
    @Override
    public void update(Catalog catalog) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CatalogDao catalogDao = MybatisUtil.getMapper(sqlSession, CatalogDao.class);
            //3 执行操作
            catalogDao.update(catalog);
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
            CatalogDao catalogDao = MybatisUtil.getMapper(sqlSession, CatalogDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id->catalogDao.delete(id));
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public List<Catalog> findAll() {
        SqlSession sqlSession = null;
        List<Catalog> list=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CatalogDao catalogDao = MybatisUtil.getMapper(sqlSession, CatalogDao.class);
            //3 执行操作释放资源
            list=catalogDao.findAll();
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }
}