package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Module;

import java.util.List;
import java.util.Map;

/**
 * @author xz
 */
public interface ModuleService {
    /**
     * 分页查询数据
     *
     * @return 数据集合
     */
    PageInfo<Module> findByPage(int currPage, int pageSize);
    /**
     * 添加数据
     * @param module 要添加的对象
     */
    void save(Module module);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Module findById(String id);
    /**
     * 修改数据
     * @param module 要修改的对象
     */
    void update(Module module);
    /**
     * 删除数据
     * @param ids 要删除数据的id们
     */
    void delete(String[] ids);

    /**
     * 查询所有数据
     * @return 数据集合
     */
    List<Module> findAll();

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<Map<String, Object>> findModulesByRoleId(String roleId);
}