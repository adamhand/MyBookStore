package com.buaa.dao;

import com.buaa.domain.Book;

import java.util.List;

public interface BookDao {
    /**
     * 向书中添加书籍。
     * @param book
     */
    void addBook(Book book);

    /**
     * 得到所有书的总数量。
     * @return
     */
    int getTotalRecordsNum();

    /**
     * 得到分类号为categoryId的书籍的总数量。
     * @param categoryId
     * @return
     */
    int getTotalRecordsNum(String categoryId);

    /**
     * 得到数据库中索引范围为startIndex-startIndex+size的所有书籍。
     * @param startIndex
     * @param size
     * @return
     */
    List<Book> findPageBooks(int startIndex, int size);

    /**
     * 得到数据库中索引范围为startIndex-startIndex+size并且分类号为categoryId的所有书籍。
     * @param startIndex
     * @param pageSize
     * @param categoryId
     * @return
     */
    List<Book> findPageBooks(int startIndex, int pageSize, String categoryId);

    /**
     * 得到ID为bookdId的书籍。
     * @param bookId
     * @return
     */
    Book findOne(String bookId);
}
