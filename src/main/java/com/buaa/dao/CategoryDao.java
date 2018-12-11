package com.buaa.dao;

import com.buaa.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 新增一个图书分类。
     * @param category
     */
    void save(Category category);

    /**
     * 查询所有图书分类。
     * @return
     */
    List<Category> findAll();

    /**
     * 根据分类名查找分类。
     * @param categoryName
     * @return
     */
    Category findByName(String categoryName);

    /**
     * 根据分类名删除分类。
     * @param categoryName
     */
    void deleteByName(String categoryName);

    /**
     * 根据分类号查找某一个分类。
     * @param categoryId
     * @return
     */
    Category findOne(String categoryId);
}
