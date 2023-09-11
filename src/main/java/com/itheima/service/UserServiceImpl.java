package com.itheima.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.MD5Util;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class UserServiceImpl implements UserService {
    @Override
    public PageInfo<User> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<User> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            UserDao UserDao = MybatisUtil.getMapper(sqlSession, UserDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<User> list = UserDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(User user) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            UserDao UserDao = MybatisUtil.getMapper(sqlSession, UserDao.class);

            //生成一个唯一的id，保存到User
            String id = UUID.randomUUID().toString().replace("-", "");
            user.setId(id);

            //对密码加密
            String password = MD5Util.md5(user.getPassword());
            user.setPassword(password);

            //3 执行操作
            UserDao.save(user);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public User findById(String id) {
        SqlSession sqlSession = null;
        User User = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            UserDao UserDao = MybatisUtil.getMapper(sqlSession, UserDao.class);
            //3 执行操作释放资源
            User = UserDao.findById(id);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return User;
    }

    @Override
    public void update(User user) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            UserDao UserDao = MybatisUtil.getMapper(sqlSession, UserDao.class);
            //3 执行操作
            UserDao.update(user);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        } finally {
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
            UserDao userDao = MybatisUtil.getMapper(sqlSession, UserDao.class);
            //3 执行操作
            Stream.of(ids).forEach(
                    id -> {
                        //删除关联关系
                        userDao.deleteRoleAssociationByUserId(id);
                        userDao.delete(id);
                    }
            );
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = null;
        List<User> list = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            UserDao userDao = MybatisUtil.getMapper(sqlSession, UserDao.class);
            //3 执行操作释放资源
            list = userDao.findAll();
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }
}