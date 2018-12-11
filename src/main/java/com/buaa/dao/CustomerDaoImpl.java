package com.buaa.dao;

import com.buaa.Utils.C3P0Util;
import com.buaa.domain.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    QueryRunner qr = new QueryRunner(C3P0Util.getDateSource());

    @Override
    public void save(Customer customer) {
        String sql = "insert into customers(id,username,password,phone,address,email,code,isActived) values(?,?,?,?,?,?,?,?)";
        try {
            qr.update(sql, customer.getId(), customer.getUsername(), customer.getPassword(), customer.getPhone(), customer.getAddress(),
                           customer.getEmail(), customer.getCode(), customer.isActived());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = "update customers set username=?,password=?,phone=?,address=?,email=?,code=?,isActived=? where id=?";
        try {
            qr.update(sql, customer.getUsername(), customer.getPassword(), customer.getPhone(), customer.getAddress(), customer.getEmail(),
                                    customer.getCode(), customer.isActived(), customer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findByCode(String code) {
        String sql = "select * from customers where code=?";
        Customer customer = null;
        try {
            customer = qr.query(sql, new BeanHandler<>(Customer.class), code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer find(String name, String password) {
        String sql = "select * from customers where username=? and password=?";
        Customer customer = null;
        try {
            customer = qr.query(sql, new BeanHandler<>(Customer.class), name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
