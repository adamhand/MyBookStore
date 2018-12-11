package com.buaa.Service;

import com.buaa.domain.Function;
import com.buaa.domain.Role;
import com.buaa.domain.User;

import java.util.List;

public interface PrivilegeService {
    User login(String username, String password);

    List<Role> findUserRoles(User user);

    List<Function> findRoleFunction(Role role);
}
