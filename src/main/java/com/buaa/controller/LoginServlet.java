package com.buaa.controller;

import com.buaa.Service.PrivilegeService;
import com.buaa.Service.PrivilegeServiceImpl;
import com.buaa.domain.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet  extends HttpServlet{
    private PrivilegeService ps = new PrivilegeServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = ps.login(username, password);
        if(user == null)
        {
            response.getWriter().write("用户名或密码错误，请重新输入");
            response.setHeader("Refresh","2;URL="+request.getContextPath()+"/passport/login.jsp");
        }

        request.setAttribute("user", user);
        response.getWriter().write("登录成功");
        response.setHeader("Refresh","1;URL="+request.getContextPath()+"/manage/index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
