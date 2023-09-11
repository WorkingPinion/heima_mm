package com.itheima.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.CompanyDao;
import com.itheima.domain.Company;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class CompanyServiceImpl implements CompanyService {
    @Override
    public PageInfo<Company> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Company> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CompanyDao companyDao = MybatisUtil.getMapper(sqlSession, CompanyDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Company> list = companyDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Company company) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CompanyDao companyDao = MybatisUtil.getMapper(sqlSession, CompanyDao.class);

            //生成一个唯一的id，保存到company
            String id = UUID.randomUUID().toString().replace("-","");
            company.setId(id);

            //3 执行操作
            companyDao.save(company);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Company findById(String id) {
        SqlSession sqlSession = null;
        Company company=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CompanyDao companyDao = MybatisUtil.getMapper(sqlSession, CompanyDao.class);
            //3 执行操作释放资源
            company= companyDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return company;
    }
    @Override
    public void update(Company company) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            CompanyDao companyDao = MybatisUtil.getMapper(sqlSession, CompanyDao.class);
            //3 执行操作
            companyDao.update(company);
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
            CompanyDao companyDao = MybatisUtil.getMapper(sqlSession, CompanyDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id->companyDao.delete(id));
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }
}