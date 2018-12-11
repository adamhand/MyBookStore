package com.buaa.controller;

import com.buaa.Service.BusinessService;
import com.buaa.Service.BusinessServiceImpl;
import com.buaa.Utils.OrderNumUtil;
import com.buaa.Utils.SendMailThread;
import com.buaa.Utils.WebUtil;
import com.buaa.bean.Cart;
import com.buaa.bean.CartItem;
import com.buaa.domain.*;
import com.buaa.commons.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "ClientServlet")
public class ClientServlet extends HttpServlet {
    private BusinessService bs = new BusinessServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if("listBooks".equals(op))
        {
            listBooks(request, response);
        }
        else if("listBookByCategory".equals(op))
        {
            listBookByCategory(request, response);
        }
        else if("buyBook".equals(op))
        {
            buyBook(request, response);
        }
        else if("delOneItem".equals(op))
        {
            delOneItem(request, response);
        }
        else if("changeNum".equals(op))
        {
            changeNum(request, response);
        }
        else if("customerRegist".equals(op))
        {
            customerRegist(request, response);
        }
        else if("activeCustomer".equals(op))
        {
            activeCustomer(request, response);
        }
        else if("login".equals(op))
        {
            login(request, response);
        }
        else if("logout".equals(op))
        {
            logout(request, response);
        }
        else if("genOrder".equals(op))
        {
            genOrder(request, response);
        }
        else if("showOrders".equals(op))
        {
            showOrders(request, response);
        }
    }

    /**
     * 判断用户是否已经登录
     * @param request
     * @param response
     * @throws IOException
     */
    private void isCustomerLoggedin(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null)
        {
            response.getWriter().write("请登录");
            response.setHeader("Refresh", "2;URL="+request.getContextPath());
            return;
        }
    }

    /**
     * 订单详情
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void showOrders(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {
        //检测是否登录，什么时候set的session??
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null)
        {
            response.getWriter().write("请登录");
            response.setHeader("Refresh", "2;URL="+request.getContextPath());
            return;
        }
        List<Order> orders = bs.findOrderByCustomerId(customer.getId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/listOrders.jsp").forward(request, response);
    }

    /**
     * 生成订单
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void genOrder(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null)
        {
            response.getWriter().write("请登录");
            response.setHeader("Refresh", "2;URL="+request.getContextPath());
            return;
        }

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Order order = new Order();
        order.setOrderNum(OrderNumUtil.genOrderNum());
        order.setPrice(cart.getPrice());
        order.setNum(cart.getNumber());
        order.setCustomer(customer);

        List<OrderItem> orderItems = new ArrayList<>();
        for(Map.Entry<String, CartItem> me : cart.getItems().entrySet())
        {
            OrderItem item = new OrderItem();
            item.setId(UUID.randomUUID().toString());
            item.setNumber(me.getValue().getNumber());
            item.setPrice(me.getValue().getPrice());
            item.setBook(me.getValue().getBook());
            orderItems.add(item);
        }

        order.setItems(orderItems);
        bs.generateOrder(order);
        request.setAttribute("order", order);
        request.getRequestDispatcher("/pay.jsp").forward(request, response);
    }

    /**
     * 用户注销
     * @param request
     * @param response
     * @throws IOException
     */
    private void logout(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("customer");
        response.getWriter().write("注销成功！2秒后转向主页");
        response.setHeader("Refresh", "2;URL="+request.getContextPath());
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws IOException
     */
    private void login(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Customer customer = bs.login(username, password);
        if(customer == null)
        {
            response.getWriter().write("错误的用户名或密码错误，或者您的账户还没有激活！5秒后转向登录页");
            response.setHeader("Refresh", "5;URL="+request.getContextPath()+"/login.jsp");
            return;
        }
        request.getSession().setAttribute("customer", customer);
        response.getWriter().write("登录成功！2秒后转向主页");
        response.setHeader("Refresh", "2;URL="+request.getContextPath());
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws IOException
     */
    private void activeCustomer(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        Customer customer = bs.findByCode(code);
        if(customer == null)
        {
            response.getWriter().write("发生未知错误，请重新输入");
            return;
        }
        customer.setActived(true);
        bs.activeCustomer(customer);
        response.getWriter().write("激活成功！2秒后转向主页");
        response.setHeader("Refresh", "2;URL="+request.getContextPath());
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void customerRegist(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        Customer customer = WebUtil.fillBean(request, Customer.class);  //首先从session中得到用户信息
        customer.setCode(UUID.randomUUID().toString()); //给用户随机分配一个激活码

        SendMailThread smt = new SendMailThread(customer);//开启发送邮件线程，给用户邮箱发送一个激活邮件
        smt.start();
        bs.registCustomer(customer); //在后台给用户注册


        request.setAttribute("message", "注册成功，我们已经向您的邮件"+customer.getEmail()+"中发送了一封激活邮件，请及时激活");
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void changeNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookId = request.getParameter("bookId");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        CartItem item = cart.getItems().get(bookId);
        item.setNumber(Integer.parseInt(request.getParameter("num")));

        response.sendRedirect(request.getContextPath()+"/ShowCart.jsp");
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void delOneItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookId = request.getParameter("bookId");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.getItems().remove(bookId);

        response.sendRedirect(request.getContextPath()+"/ShowCart.jsp");
    }

    /**
     *
      * @param request
     * @param response
     */
    private void buyBook(HttpServletRequest request, HttpServletResponse response)
    {
        String bookId = request.getParameter("bookId");
        Book book = bs.fildBookById(bookId);

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null)
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.addBook2Items(book);
        request.setAttribute("message", "购买成功！<a href='javascript:window.history.back()'>返回</a>");

        try {
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    private void listBookByCategory(HttpServletRequest request, HttpServletResponse response)
    {
        List<Category> cs = bs.findAllCategories();
        request.setAttribute("cs", cs);

        String num = request.getParameter("num");
        String categoryId = request.getParameter("categoryId");
        Page page = bs.findPage(num, categoryId);

        page.setUrl("/servlet/ClientServlet?op=listBookByCategory&categoryId="+categoryId);
        request.setAttribute("commons", page);

        try {
            request.getRequestDispatcher("/listBook.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
    {
        List<Category> cs = bs.findAllCategories();
        request.setAttribute("cs", cs);

        String num = request.getParameter("num");
        Page page = bs.findPage(num);

        page.setUrl("/servlet/ClientServlet?op=listBooks");
        request.setAttribute("commons", page);

        try {
            request.getRequestDispatcher("/listBook.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
