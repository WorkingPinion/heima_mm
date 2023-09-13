package com.itheima.service;

import com.itheima.domain.QuestionItem;

import java.util.List;

/**
 * @author xz
 */
public interface QuestionItemService {
    /**
     * 根据问题id查询所有选项数据
     * @return 数据集合
     * @param questionId
     */
    List<QuestionItem> findAll(String questionId);
    /**
     * 添加数据
     * @param questionItem 要添加的对象
     */
    void save(QuestionItem questionItem);
    /**
     * 根据id查询数据
     * @param id 要查询数据的id
     * @return 返回的是查询结果的对象
     */
    QuestionItem findById(String id);
    /**
     * 修改数据
     * @param questionItem 要修改的对象
     */
    void update(QuestionItem questionItem);
    /**
     * 删除数据
     * @param id 要删除数据的id们
     */
    void delete(String id);

}