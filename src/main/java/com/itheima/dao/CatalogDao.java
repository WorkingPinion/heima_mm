package com.itheima.dao;

import com.itheima.domain.Catalog;

import java.util.List;

/**
 * @author xz
 */
public interface CatalogDao {
    /**
     * 查询所有数据
     *
     * @return 数据集合
     */
    List<Catalog> findAll();

    /**
     * 添加数据
     *
     * @param catalog 要添加的对象
     */
    void save(Catalog catalog);

    /**
     * 根据id查询数据
     *
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Catalog findById(String id);

    /**
     * 修改数据
     *
     * @param catalog 要修改的对象
     */
    void update(Catalog catalog);

    /**
     * 删除数据
     *
     * @param id 要删除数据的id
     */
    void delete(String id);
}