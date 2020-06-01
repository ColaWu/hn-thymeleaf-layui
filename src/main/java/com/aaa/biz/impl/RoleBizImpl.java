package com.aaa.biz.impl;

import com.aaa.biz.RoleBiz;
import com.aaa.dao.RoleMapper;
import com.aaa.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 陈建
 * @Date: 2020/5/29 0029 16:36
 * @Version 1.0
 */
@Service
public class RoleBizImpl implements RoleBiz {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> selectAllRole() {
        return roleMapper.selectAllRole();
    }

    public int insertSelective(Role role)
    {
        return roleMapper.insertSelective(role);
    }

    public int updateByPrimaryKeySelective(Role role){
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    public int SoftdelRoleByID(List<String> list)
    {
        return roleMapper.SoftdelRoleByID(list);
    }

    public Role selectLast(){
        return roleMapper.selectLast();
    }
    public int updateByRoleNameSelective(Role record){
        return roleMapper.updateByRoleNameSelective(record);
    }
    public Role selectByRoleName(String roleName){
        return roleMapper.selectByRoleName(roleName);
    }

    public Role selectByRoleKey(String roleKey)
    {
        return roleMapper.selectByRoleKey(roleKey);
    }
    public Role selectByKey(String roleKey,int roleId){
        return roleMapper.selectByKey(roleKey,roleId);
    }
    public Role selectByName(String roleName,int roleId){
        return roleMapper.selectByName(roleName,roleId);
    }
    public int insertMenu(int roleId,int menuId){return roleMapper.insertMenu(roleId,menuId);}
}
