package com.itheima.dao;

import com.itheima.domain.Course;

import java.util.List;

/**
 * @author xz
 */
public interface CourseDao {
    /**
     * 查询所有数据
     *
     * @return 数据集合
     */
    List<Course> findAll();

    /**
     * 添加数据
     *
     * @param course 要添加的对象
     */
    void save(Course course);

    /**
     * 根据id查询数据
     *
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Course findById(String id);

    /**
     * 修改数据
     *
     * @param course 要修改的对象
     */
    void update(Course course);

    /**
     * 删除数据
     *
     * @param id 要删除数据的id
     */
    void delete(String id);
}