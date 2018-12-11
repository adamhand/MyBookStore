package com.buaa.dao;

import com.buaa.Utils.C3P0Util;
import com.buaa.domain.Function;
import com.buaa.domain.Role;
import com.buaa.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class PrivilegeDaoImpl implements PrivilegeDao {
    QueryRunner qr = new QueryRunner(C3P0Util.getDateSource());

    @Override
    public User findUser(String username, String password) {
        String sql = "select * from users where username=? and password=?";
        User user = null;
        try {
            user = qr.query(sql, new BeanHandler<>(User.class), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Role> findUserRoles(User user) {
        String sql = "select * from roles where id in (select r_id from user_role where u_id=?)";
        List<Role> roles = null;
        try {
            roles = qr.query(sql, new BeanListHandler<>(Role.class), user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public List<Function> findRoleFunction(Role role) {
        String sql = "select * from functions where id in (select f_id from role_function where r_id=?";
        List<Function> functions = null;
        try {
            functions = qr.query(sql, new BeanListHandler<>(Function.class), role.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return functions;
    }
}
