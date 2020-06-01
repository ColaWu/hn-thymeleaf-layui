package com.aaa.dao;

import com.aaa.entity.MyUserInfo;
import com.aaa.entity.Role;
import com.aaa.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByUsername(String username);
    int delUserByID( @Param("ids") List<String> ids);
    List<User> selectAllUser();
    int resetPassword(User user);//重置密码
    int SoftdeleteByPrimaryKey(Integer id);//单行软删除
    int SoftdelUserByID(@Param("ids") List<String> ids);//多行软删除
    List<MyUserInfo> selectByPhoneandLoginName(String phonenumber, String loginName);//查询条件
    int insertUserRole(Integer userId, Role role);//插入中间表
    int selectStatus(String loginname);//查状态
}