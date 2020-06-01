package com.aaa.dao;

import com.aaa.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    Role selectByRoleName(String roleName);
    Role selectByName(String roleName,int roleId);

    Role selectByRoleKey(String roleKey);

    Role selectByKey(String roleKey,int roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    int updateByRoleNameSelective(Role record);

    List<Role> selectAllRole();

    int SoftdelRoleByID(@Param("ids") List<String> ids);

    Role selectLast();
    int insertMenu(int roleId,int menuId);
}