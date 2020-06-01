package com.aaa.biz;

import com.aaa.entity.Role;

import java.util.List;

/**
 * @Author: 陈建
 * @Date: 2020/5/29 0029 16:36
 * @Version 1.0
 */
public interface  RoleBiz {
   List<Role> selectAllRole();
   int insertSelective(Role role);
   int updateByPrimaryKeySelective(Role role);
   int SoftdelRoleByID(List<String> list);
   Role selectLast();
   int updateByRoleNameSelective(Role record);
   Role selectByRoleName(String roleName);
   Role selectByRoleKey(String roleKey);
   Role selectByKey(String roleKey,int roleId);
   Role selectByName(String roleName,int roleId);
   int insertMenu(int roleId,int menuId);

}
