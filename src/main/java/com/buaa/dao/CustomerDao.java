package com.buaa.dao;

import com.buaa.domain.Customer;

public interface CustomerDao {
    /**
     * 新增用户。
     * @param customer
     */
    void save(Customer customer);

    /**
     * 更新用户信息。
     * @param customer
     */
    void update(Customer customer);

    /**
     * 根据激活码查询用户。
     * @param code
     * @return
     */
    Customer findByCode(String code);

    /**
     * 根据用户名和密码查询用户。
     * @param name
     * @param password
     * @return
     */
    Customer find(String name, String password);
}
