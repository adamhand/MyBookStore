package com.buaa.domain;

import com.buaa.domain.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 总订单的属性。
 */
public class Order implements Serializable {
    private String orderNum;
    private float price;
    private int num;
    private int status;

    private Customer customer;
    private List<OrderItem> items = new ArrayList<OrderItem>();

    public String getOrderNum() {
        return orderNum;
    }

    public float getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public int getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
