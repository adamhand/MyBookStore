package com.buaa.dao;

import com.buaa.Utils.C3P0Util;
import com.buaa.domain.Book;
import com.buaa.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class BookDaoImp implements BookDao {
    private QueryRunner qr = new QueryRunner(C3P0Util.getDateSource());

    @Override
    public void addBook(Book book) {
        String sql = "insert into books(id,name,author,price,path,filename,des,categoryId) values (?,?,?,?,?,?,?,?)";
        try {
            qr.update(sql, book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getPath(), book.getFilename(), book.getDescription(),book.getCategory());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getTotalRecordsNum() {
        String sql = "select count(id) from books";
        Long num = null;
        try {
            num = (Long) qr.query(sql, new ScalarHandler<>(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num.intValue();
    }

    @Override
    public int getTotalRecordsNum(String categoryId) {
        String sql = "select count(id) from books where categoryId = ?";
        Long num = null;
        try {
            num = (Long) qr.query(sql, new ScalarHandler<>(1), categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num.intValue();
    }

    @Override
    public List<Book> findPageBooks(int startIndex, int pageSize, String categoryId) {
        String sql = "select * from books where categoryId=? limit ?,?";
        List<Book> books = null;
        try {
            books = qr.query(sql, new BeanListHandler<>(Book.class), categoryId, startIndex, pageSize);
            if(books != null)
            {
                for(Book book : books)
                {
                    String sqlCate = "select * from categorys where id=?";
                    Category category = qr.query(sqlCate, new BeanHandler<>(Category.class), categoryId);
                    book.setCategory(category);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findPageBooks(int startIndex, int size) {
        String sql = "select * from books limit ? , ?";
        List<Book> books = null;
        try {
            books = qr.query(sql, new BeanListHandler<Book>(Book.class), startIndex, size);
            if(books != null)
            {
                for(Book book : books)
                {
                    //根据书的ID得到书的categoryId，再根据categoryId得到书的category属性，然后将属性值赋值给书籍
                    //这样做的原因是在db.sql数据库中的books表中只定义了categoryId，没有定义整个的category属性。
                    String sqlCate = "select * from categorys where id=(select categoryId from books where id=?)";
                    Category category = qr.query(sqlCate, new BeanHandler<Category>(Category.class), book.getId());
                    book.setCategory(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book findOne(String bookId) {
        String sql = "select * from books where id=?";
        Book book = null;
        try {
            book = qr.query(sql, new BeanHandler<>(Book.class), bookId);
            if(book != null)
            {
                sql = "select * from categorys where id=(select categoryId from books where id=?)";
                Category category = qr.query(sql, new BeanHandler<>(Category.class), book.getId());
                book.setCategory(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}
