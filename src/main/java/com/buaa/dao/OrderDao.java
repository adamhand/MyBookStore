package com.buaa.dao;

import com.buaa.domain.Order;
import com.buaa.domain.OrderItem;

import java.util.List;

public interface OrderDao {
    /**
     * 新增订单。
     * @param order
     */
    void save(Order order);

    /**
     * 根据订单号查询订单。
     * @param orderNum
     * @return
     */
    Order findByNum(String orderNum);

    /**
     * 更新订单。
     * @param order
     */
    void update(Order order);

    /**
     * 根据用户名查询订单号。
     * @param customerId
     * @return
     */
    List<Order> findByCustomerId(String customerId);

    /**
     * 根据订单号查询订单详情。
     * @param orderNum
     * @return
     */
    List<OrderItem> findOrderItem(String orderNum);
}
