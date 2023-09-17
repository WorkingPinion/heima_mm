package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.ModuleDao;
import com.itheima.domain.Module;
import com.itheima.service.ModuleService;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class ModuleServiceImpl implements ModuleService {
    @Override
    public PageInfo<Module> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Module> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Module> list = moduleDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Module module) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);

            //生成一个唯一的id，保存到module
            String id = UUID.randomUUID().toString().replace("-","");
            module.setId(id);

            //3 执行操作
            moduleDao.save(module);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Module findById(String id) {
        SqlSession sqlSession = null;
        Module module=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);
            //3 执行操作释放资源
            module= moduleDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return module;
    }
    @Override
    public void update(Module module) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);
            //3 执行操作
            moduleDao.update(module);
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
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id->moduleDao.delete(id));
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public List<Module> findAll() {
        SqlSession sqlSession = null;
        List<Module> list=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);
            //3 执行操作释放资源
            list=moduleDao.findAll();
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }

    /**
     * 根据角色id初始化属性控件
     * @param roleId
     * @return
     */
    @Override
    public List<Map<String, Object>> findModulesByRoleId(String roleId) {
        List<Map<String, Object>> list = null;
        SqlSession sqlSession=null;
        try {
            //1 获取SqlSession对象
            sqlSession= MybatisUtil.getSqlSession();
            //2 获取dao对应的代理对象
            ModuleDao moduleDao = MybatisUtil.getMapper(sqlSession, ModuleDao.class);
            //3 执行操作
            //3.2 查询所有
            list = moduleDao.findModulesByRoleId(roleId);
            //4 返回结果并释放资源
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }
}