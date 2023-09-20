package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.RoleDao;
import com.itheima.domain.Role;
import com.itheima.service.RoleService;
import com.itheima.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author xz
 */
public class RoleServiceImpl implements RoleService {
    @Override
    public PageInfo<Role> findByPage(int currPage, int pageSize) {
        SqlSession sqlSession = null;
        PageInfo<Role> info = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作释放资源
            //3.1 在查询所有之前设置分页参数
            PageHelper.startPage(currPage, pageSize);
            //3.2 查询所有
            List<Role> list = roleDao.findAll();
            //3.3 封装分页结果
            info = new PageInfo<>(list);
        } finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return info;
    }

    @Override
    public void save(Role role) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);

            //生成一个唯一的id，保存到role
            String id = UUID.randomUUID().toString().replace("-","");
            role.setId(id);

            //3 执行操作
            roleDao.save(role);
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public Role findById(String id) {
        SqlSession sqlSession = null;
        Role role=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作释放资源
            role= roleDao.findById(id);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return role;
    }
    @Override
    public void update(Role role) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作
            roleDao.update(role);
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
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作
            Stream.of(ids).forEach(id->roleDao.delete(id));
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
    }

    @Override
    public List<Role> findAll() {
        SqlSession sqlSession = null;
        List<Role> list=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作释放资源
            list=roleDao.findAll();
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return list;
    }

    /**
     * 更新角色绑定的模块
     * @param roleId
     * @param moduleIds
     */
    @Override
    public void updateRoleAndModules(String roleId, String[] moduleIds) {
        SqlSession sqlSession = null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作
            //3.1 删除角色绑定的所有模块，操作ss_role_module
            roleDao.deleteModulesByRoleId(roleId);
            //3.2 遍历moduleIds，给角色绑定新的module，操作ss_role_module
            Stream.of(moduleIds).forEach(moduleId->{
                roleDao.saveModulesByRoleId(roleId,moduleId);
            });
            //4 提交事务
            MybatisUtil.commit(sqlSession);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }

    }

    /**
     * 查询用户绑定的角色id们
     * @param userId
     * @return
     */
    @Override
    public String[] findRoleIdsByUserId(String userId) {
        SqlSession sqlSession = null;
        String[] checkedRoleIds=null;
        try {
            //1 通过MybatisUtil工厂类获取SqlSession对象
            sqlSession = MybatisUtil.getSqlSession();
            //2 通过MybatisUtil工厂类获取dao接口的代理对象
            RoleDao roleDao = MybatisUtil.getMapper(sqlSession, RoleDao.class);
            //3 执行操作释放资源
            checkedRoleIds= roleDao.findRoleIdsByUserId(userId);
        }finally {
            //释放资源
            MybatisUtil.close(sqlSession);
        }
        return checkedRoleIds;
    }

}