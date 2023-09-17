package com.itheima.dao;

import com.itheima.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xz
 */
public interface RoleDao {
    /**
     * 查询所有数据
     *
     * @return 数据集合
     */
    List<Role> findAll();

    /**
     * 添加数据
     *
     * @param role 要添加的对象
     */
    void save(Role role);

    /**
     * 根据id查询数据
     *
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Role findById(String id);

    /**
     * 修改数据
     *
     * @param role 要修改的对象
     */
    void update(Role role);

    /**
     * 删除数据
     *
     * @param id 要删除数据的id
     */
    void delete(String id);

    /**
     * 删除角色绑定的所有模块
     * @param roleId
     */
    void deleteModulesByRoleId(String roleId);
    /**
     * 给角色绑定新的module
     * @param roleId
     * @param moduleId
     */
    void saveModulesByRoleId(@Param("roleId") String roleId, @Param("moduleId") String moduleId);


}