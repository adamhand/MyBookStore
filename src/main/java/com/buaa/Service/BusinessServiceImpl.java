package com.buaa.Service;

import com.buaa.dao.*;
import com.buaa.domain.*;
import com.buaa.commons.Page;

import java.util.List;
import java.util.UUID;

public class BusinessServiceImpl implements BusinessService {
    private BookDao bookDao = new BookDaoImp();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();
    private CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public void addCategory(Category category) {
        category.setId(UUID.randomUUID().toString());
        categoryDao.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public boolean isCategoryExists(String categoryName) {
        Category category = categoryDao.findByName(categoryName);
        return category == null ? false : true;
    }

    @Override
    public void deleteCategoryByName(String categoryName) {
        categoryDao.deleteByName(categoryName);
    }

    @Override
    public void addBook(Book book) {
        if(book==null)
            throw new IllegalArgumentException("error");
        if(book.getCategory()==null)
            throw new IllegalArgumentException("error");
        book.setId(UUID.randomUUID().toString());
        bookDao.addBook(book);

    }

    @Override
    public Category findCategoryById(String categoryId) {
        return categoryDao.findOne(categoryId);
    }

    @Override
    public Page findPage(String num) {
        int pageNum = 1;
        if(num != null)
        {
            pageNum = Integer.parseInt(num);
        }
        int totalRecordsNum = bookDao.getTotalRecordsNum();
        Page page = new Page(pageNum, totalRecordsNum);
        List<Book> records = bookDao.findPageBooks(page.getStartIndex(), page.getPageSize());
        page.setRecords(records);

        return page;
    }

    @Override
    public Page findPage(String num, String categoryId) {
        int pageNum = 1;
        if(num != null)
        {
            pageNum = Integer.parseInt(num);
        }
        int totalRecordsNum = bookDao.getTotalRecordsNum(categoryId);
        Page page = new Page(pageNum, totalRecordsNum);
        List<Book> records = bookDao.findPageBooks(page.getStartIndex(), page.getTotalPageSize());
        page.setRecords(records);

        return page;
    }

    @Override
    public Book fildBookById(String bookId) {
        return bookDao.findOne(bookId);
    }

    @Override
    public void registCustomer(Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        customerDao.save(customer);
    }

    @Override
    public Customer findByCode(String code) {
        return customerDao.findByCode(code);
    }

    @Override
    public void activeCustomer(Customer customer) {
        if(customer == null)
        {
            throw new  RuntimeException("数据不能为空！");
        }
        if(customer.getId() == null)
        {
            throw new RuntimeException("更新数据的主键不能为空！");
        }
        customerDao.update(customer);
    }

    @Override
    public Customer login(String username, String password) {
        Customer customer = customerDao.find(username, password);
        if(customer == null || (! customer.isActived()))
        {
            return null;
        }

        return customer;
    }

    @Override
    public void generateOrder(Order order) {
        if(order == null)
        {
            throw new RuntimeException("订单不能为空！");
        }else if(order.getCustomer() == null)
        {
            throw new RuntimeException("订单客户不能为空！");
        }
        orderDao.save(order);
    }

    @Override
    public Order findOrderByNum(String orderNum) {
        return orderDao.findByNum(orderNum);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    @Override
    public void changeOrderStatus(int status, String orderNum) {
        Order order = orderDao.findByNum(orderNum);
        order.setStatus(status);
        updateOrder(order);
    }

    @Override
    public List<Order> findOrderByCustomerId(String customerId) {
        return orderDao.findByCustomerId(customerId);
    }

    @Override
    public List<OrderItem> findOrderItemByOrderNum(String orderNum) {
        return orderDao.findOrderItem(orderNum);
    }
}
