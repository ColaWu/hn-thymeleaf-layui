package com.aaa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMenuMapper
{
    int insertMenuId(Integer roleId, Integer menuId);
    int deleteByRoleId(Integer roleId);
}
