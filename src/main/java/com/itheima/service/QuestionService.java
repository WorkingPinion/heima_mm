package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Question;

/**
 * @author xz
 */
public interface QuestionService {
    /**
     * 分页查询数据
     *
     * @return 数据集合
     */
    PageInfo<Question> findByPage(int currPage, int pageSize);
    /**
     * 添加数据
     * @param question 要添加的对象
     */
    void save(Question question);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    Question findById(String id);
    /**
     * 修改数据
     * @param question 要修改的对象
     */
    void update(Question question);
    /**
     * 删除数据
     * @param ids 要删除数据的id们
     * @param uploadPath
     */
    void delete(String[] ids, String uploadPath);
}