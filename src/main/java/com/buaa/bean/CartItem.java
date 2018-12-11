package com.buaa.bean;

import com.buaa.domain.Book;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Book book;      //该项对应的书籍
    private float price;    //小计：单价 * 数量
    private int number;     //书籍数量

    public CartItem(Book book)
    {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public float getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
