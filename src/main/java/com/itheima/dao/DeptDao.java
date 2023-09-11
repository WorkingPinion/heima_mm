package com.itheima.dao;

import com.itheima.domain.Dept;

import java.util.List;

/**
 * @author xz
 */
public interface DeptDao {
    /**
     * 查询所有数据
     *
     * @return 数据集合
     */
    List<Dept> findAll();

    /**
     * 添加数据
     *
     * @param Dept 要添加的对象
     */
    void save(Dept Dept);

    /**
     * 根据id查询数据
     *
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Dept findById(String id);

    /**
     * 修改数据
     *
     * @param Dept 要修改的对象
     */
    void update(Dept Dept);

    /**
     * 删除数据
     *
     * @param id 要删除数据的id
     */
    void delete(String id);


}