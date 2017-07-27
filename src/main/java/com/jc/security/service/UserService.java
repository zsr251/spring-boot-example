package com.jc.security.service;

import com.jc.security.model.Permission;
import com.jc.security.model.Role;
import com.jc.security.model.User;

import java.util.List;

/**
 * Created by jasonzhu on 2017/7/26.
 */
public interface UserService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 根据用户ID查找角色
     * @param userId
     * @return
     */
    List<Role> findRoleByUserId(Integer userId);

    /**
     * 根据用户ID查找权限
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(Integer userId);

}
