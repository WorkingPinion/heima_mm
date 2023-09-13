package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Course;

import java.util.List;

/**
 * @author xz
 */
public interface CourseService {
    /**
     * 分页查询数据
     *
     * @return 数据集合
     */
    PageInfo<Course> findByPage(int currPage, int pageSize);
    /**
     * 添加数据
     * @param course 要添加的对象
     */
    void save(Course course);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Course findById(String id);
    /**
     * 修改数据
     * @param course 要修改的对象
     */
    void update(Course course);
    /**
     * 删除数据
     * @param ids 要删除数据的id们
     */
    void delete(String[] ids);
    /**
     * 查询所有数据
     * @return 数据集合
     */
    List<Course> findAll();
}