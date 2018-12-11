package com.buaa.dao;

import com.buaa.domain.Function;
import com.buaa.domain.Role;
import com.buaa.domain.User;

import java.util.List;

public interface PrivilegeDao {
    /**
     * 根据用户名和密码查找用户。
     * @param username
     * @param password
     * @return
     */
    User findUser(String username, String password);

    /**
     * 根据user.ID查询用户权限。
     * @param user
     * @return
     */
    List<Role> findUserRoles(User user);

    /**
     * 根据role.ID寻找role的function
     * @param role
     * @return
     */
    List<Function> findRoleFunction(Role role);
}
