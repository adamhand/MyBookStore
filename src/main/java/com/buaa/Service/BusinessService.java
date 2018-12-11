package com.buaa.Service;

import com.buaa.domain.*;
import com.buaa.commons.Page;

import java.util.List;

public interface BusinessService {
    public void addCategory(Category category);

    public List<Category> findAllCategories();

    public boolean isCategoryExists(String categoryName);

    public void deleteCategoryByName(String categoryName);

    //添加书籍信息
    void addBook(Book book);

    public Category findCategoryById(String categoryId);



    /**
     * 根据页码num返回该页的信息，包含所有的书籍信息。
     */
    Page findPage(String num);

    /**
     * 根据页码和书籍种类返回该页的信息，只包括categoryId类的书籍信息。
     * @param num
     * @param categoryId
     * @return
     */
    Page findPage(String num, String categoryId);

    Book fildBookById(String bpookId);

    void registCustomer(Customer customer);


    Customer findByCode(String code);

    /**
     * 更新用户信息。
     * @param customer
     */
    void activeCustomer(Customer customer);

    Customer login(String username, String password);

    /**
     * 生成订单。
     * @param order
     */
    void generateOrder(Order order);

    Order findOrderByNum(String orderNum);

    void updateOrder(Order order);

    /**
     * 将订单号为orderNum的订单状态改为status。
     * @param status
     * @param orderNum
     */
    void changeOrderStatus(int status, String orderNum);

    List<Order> findOrderByCustomerId(String customerId);

    /**
     * 根据订单号查看订单详情。
     * @param customerId
     * @return
     */
    List<OrderItem> findOrderItemByOrderNum(String customerId);
}
