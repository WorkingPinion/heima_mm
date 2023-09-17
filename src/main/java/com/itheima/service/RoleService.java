package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Role;

import java.util.List;

/**
 * @author xz
 */
public interface RoleService {
    /**
     * 分页查询数据
     *
     * @return 数据集合
     */
    PageInfo<Role> findByPage(int currPage, int pageSize);
    /**
     * 添加数据
     * @param role 要添加的对象
     */
    void save(Role role);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Role findById(String id);
    /**
     * 修改数据
     * @param role 要修改的对象
     */
    void update(Role role);
    /**
     * 删除数据
     * @param ids 要删除数据的id们
     */
    void delete(String[] ids);

    /**
     * 查询所有数据
     * @return 数据集合
     */
    List<Role> findAll();

    /**
     * 更新角色绑定的模块
     * @param roleId
     * @param moduleIds
     */
    void updateRoleAndModules(String roleId, String[] moduleIds);


}