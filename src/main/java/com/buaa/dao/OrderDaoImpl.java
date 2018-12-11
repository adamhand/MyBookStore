package com.buaa.dao;

import com.buaa.Utils.C3P0Util;
import com.buaa.domain.Book;
import com.buaa.domain.Customer;
import com.buaa.domain.Order;
import com.buaa.domain.OrderItem;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    QueryRunner qr = new QueryRunner(C3P0Util.getDateSource());

    @Override
    public void save(Order order) {
        String sql = "insert into orders(ordernum,price,num,status,customerId) values(?,?,?,?,?)";
        try {
            qr.update(sql, order.getOrderNum(), order.getPrice(), order.getNum(), order.getStatus(),
                           order.getCustomer()==null?null:order.getCustomer().getId());
            List<OrderItem> orderItems = order.getItems();
            sql = "insert into orderitems (id,num,price,ordernum,bookid) values(?,?,?,?,?)";
            for(OrderItem item : orderItems)
            {
                qr.update(sql, item.getId(), item.getNumber(), item.getPrice(), item.getBook()==null?null:item.getBook().getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findByNum(String orderNum) {
        String sql = "select * from orders where ordernum=?";
        Order order = null;
        try {
            qr.query(sql, new BeanHandler<>(Order.class), orderNum);
            if(order != null)
            {
                sql = "select * from customers where id=(select customerId from orders where ordernum=?)";
                Customer customer = qr.query(sql, new BeanHandler<>(Customer.class), orderNum);
                order.setCustomer(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void update(Order order) {
        String sql = "update order set price=?,num=?,status=? where ordernum=?";
        try {
            qr.update(sql, order.getPrice(), order.getNum(), order.getStatus(), order.getOrderNum());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findByCustomerId(String customerId){
        String sql = "select * from orders where customerId=? order by ordernum desc";   //按照ordernum排序
        List<Order> orders = null;
        try {
            orders = qr.query(sql, new BeanListHandler<>(Order.class), customerId);
            if(orders != null)
            {
                sql = "select * from customers where customerId=?";
                Customer customer = qr.query(sql, new BeanHandler<>(Customer.class), customerId);
                for(Order order : orders)
                {
                    order.setCustomer(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    @Override
    public List<OrderItem> findOrderItem(String orderNum) {
        String sql = "select * from orderitems where ordernum=?";
        List<OrderItem> orderItems = null;
        try {
            orderItems = qr.query(sql, new BeanListHandler<>(OrderItem.class), orderNum);
            if(orderItems != null)
            {
                for(OrderItem orderItem : orderItems)
                {
                    sql = "select * from books where id=(select bookId from orderitems where id=?)";
                    Book book = qr.query(sql, new BeanHandler<>(Book.class), orderItem.getId());
                    orderItem.setBook(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}
