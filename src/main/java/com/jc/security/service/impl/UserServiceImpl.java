package com.jc.security.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jc.security.mapper.RolePermissionMapper;
import com.jc.security.mapper.UserMapper;
import com.jc.security.mapper.UserRoleMapper;
import com.jc.security.model.Permission;
import com.jc.security.model.Role;
import com.jc.security.model.User;
import com.jc.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jasonzhu on 2017/7/27.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public User findByUserName(String username) {
        Preconditions.checkNotNull(username, "用户名不能为空");
        User record = new User();
        record.setUsername(username);
        List<User> list = userMapper.select(record);
        return list == null || list.size() < 1 ? null : list.get(0);
    }

    @Override
    public List<Role> findRoleByUserId(Integer userId) {
        Preconditions.checkNotNull(userId, "用户ID不能为空");
        List<Role> list = userRoleMapper.findRoleByUserId(userId);
        return list == null ? Lists.newArrayList() : list;
    }

    @Override
    public List<Permission> findPermissionByUserId(Integer userId) {
        Preconditions.checkNotNull(userId, "用户ID不能为空");
        List<Permission> list = rolePermissionMapper.findPermissionByUserId(userId);
        return list == null ? Lists.newArrayList() : list;
    }
}
