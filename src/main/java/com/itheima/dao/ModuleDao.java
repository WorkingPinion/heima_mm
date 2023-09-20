package com.itheima.dao;

import com.itheima.domain.Module;

import java.util.List;
import java.util.Map;

/**
 * @author xz
 */
public interface ModuleDao {
    /**
     * 查询所有数据
     *
     * @return 数据集合
     */
    List<Module> findAll();

    /**
     * 添加数据
     *
     * @param module 要添加的对象
     */
    void save(Module module);

    /**
     * 根据id查询数据
     *
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Module findById(String id);

    /**
     * 修改数据
     *
     * @param module 要修改的对象
     */
    void update(Module module);

    /**
     * 删除数据
     *
     * @param id 要删除数据的id
     */
    void delete(String id);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<Map<String, Object>> findModulesByRoleId(String roleId);

    /**
     * 根据用户id动态查询用户的菜单
     */
    List<Module> findMenuByUserId(String userId);
    /**
     * 根据模块id动态查询子菜单
     */
    List<Module> findModuleByParentId(String moduleId);


    /**
     * 查询用户具有的操作权限
     * @param userId
     * @return
     */
    List<String> findPermissionByUserId(String userId);
}