package com.buaa.dao;

import com.buaa.Utils.C3P0Util;
import com.buaa.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    QueryRunner qr = new QueryRunner(C3P0Util.getDateSource());

    @Override
    public void save(Category category) {
        String sql = "insert into categorys(id,name,des) values(?,?,?)";
        try {
            qr.update(sql,category.getId(), category.getName(), category.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> findAll() {
        String sql = "select * from categorys";
        List<Category> categories = null;
        try {
            categories = qr.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findByName(String categoryName) {
        String sql = "select * from categorys where name=?";
        Category category = null;
        try {
            category = qr.query(sql, new BeanHandler<>(Category.class), categoryName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void deleteByName(String categoryName) {
        String sql = "delete from categorys where name=?";
        try {
            qr.update(sql, categoryName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category findOne(String categoryId) {
        String sql = "select * from categorys where id=?";
        Category category = null;
        try {
            category = qr.query(sql, new BeanHandler<>(Category.class), categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
