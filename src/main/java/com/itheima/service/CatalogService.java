package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Catalog;

import java.util.List;

/**
 * @author xz
 */
public interface CatalogService {
    /**
     * 分页查询数据
     *
     * @return 数据集合
     */
    PageInfo<Catalog> findByPage(int currPage, int pageSize);
    /**
     * 添加数据
     * @param catalog 要添加的对象
     */
    void save(Catalog catalog);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Catalog findById(String id);
    /**
     * 修改数据
     * @param catalog 要修改的对象
     */
    void update(Catalog catalog);
    /**
     * 删除数据
     * @param ids 要删除数据的id们
     */
    void delete(String[] ids);

    /**
     * 查询所有数据
     * @return 数据集合
     */
    List<Catalog> findAll();
}