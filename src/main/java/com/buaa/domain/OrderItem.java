package com.buaa.domain;

import java.io.Serializable;

/**
 * 单个订单的属性。
 */
public class OrderItem implements Serializable {
    private String id;        //订单的编号
    private int number;       //该订单包含的图书数量
    private float price;      //该订单的总价格
    private Book book;

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public float getPrice() {
        return price;
    }

    public Book getBook() {
        return book;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
