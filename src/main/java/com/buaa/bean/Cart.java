package com.buaa.bean;

import com.buaa.domain.Book;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private Map<String, CartItem> items = new HashMap<>();   //String-bookId
    private float price;    //总价格
    private int number;     //总数量

    public Map<String, CartItem> getItems() {
        return items;
    }

    public void addBook2Items(Book book)
    {
        //若书籍存在。
        if(items.containsKey(book.getId()))
        {
            CartItem cartItem = items.get(book.getId());
            cartItem.setNumber(cartItem.getNumber() + 1);
        }//若书籍不存在。
        else
        {
            CartItem cartItem = new CartItem(book);
            cartItem.setNumber(1);
            items.put(book.getId(), cartItem);
        }
    }

    public float getPrice() {
        float price = 0;
        for(Map.Entry<String, CartItem> me : items.entrySet())
        {
            price += me.getValue().getPrice();
        }

        return price;
    }

    public int getNumber() {
        int number = 0;
        for(Map.Entry<String, CartItem> me : items.entrySet())
        {
            number += me.getValue().getNumber();
        }

        return number;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
