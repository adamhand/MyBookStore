package com.buaa.Service;

import com.buaa.dao.PrivilegeDao;
import com.buaa.dao.PrivilegeDaoImpl;
import com.buaa.domain.Function;
import com.buaa.domain.Role;
import com.buaa.domain.User;

import java.util.List;

public class PrivilegeServiceImpl implements PrivilegeService {
    PrivilegeDao privilegeDao = new PrivilegeDaoImpl(); //多态？？声明一个dao可以使用daoimpl的函数??

    @Override
    public User login(String username, String password) {
        return privilegeDao.findUser(username, password);
    }

    @Override
    public List<Role> findUserRoles(User user) {
        return privilegeDao.findUserRoles(user);
    }

    @Override
    public List<Function> findRoleFunction(Role role)
    {
        return privilegeDao.findRoleFunction(role);
    }
}
