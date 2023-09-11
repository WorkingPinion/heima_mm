package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Company;

/**
 * @author xz
 */
public interface CompanyService {
    /**
     * 分页查询数据
     *
     * @return 数据集合
     */
    PageInfo<Company> findByPage(int currPage, int pageSize);
    /**
     * 添加数据
     * @param company 要添加的对象
     */
    void save(Company company);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Company findById(String id);
    /**
     * 修改数据
     * @param company 要修改的对象
     */
    void update(Company company);
    /**
     * 删除数据
     * @param ids 要删除数据的id们
     */
    void delete(String[] ids);
}