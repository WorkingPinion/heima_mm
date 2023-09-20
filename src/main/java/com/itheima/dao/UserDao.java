package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xz
 */
public interface UserDao {
    /**
     * 查询所有数据
     *
     * @return 数据集合
     */
    List<User> findAll();

    /**
     * 添加数据
     *
     * @param user 要添加的对象
     */
    void save(User user);

    /**
     * 根据id查询数据
     *
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    User findById(String id);

    /**
     * 修改数据
     *
     * @param user 要修改的对象
     */
    void update(User user);

    /**
     * 删除数据
     *
     * @param id 要删除数据的id
     */
    void delete(String id);

    /**
     * 根据id删除关联的角色信息
     * @param id 用户id
     */
    void deleteRoleAssociationByUserId(String id);

    /**
     * 根据用户id删除绑定的角色，操作ss_role_user
     * @param userId
     */
    void deleteUserAndRoles(String userId);

    /**
     * 保存用户绑定的角色，操作ss_role_user
     * @param userId
     * @param roleId
     */
    void saveUserAndRoles(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 根据邮箱和密码查询用户信息
     * @param email
     * @param password
     * @return
     */
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}