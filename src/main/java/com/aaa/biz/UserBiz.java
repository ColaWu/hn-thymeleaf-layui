package com.aaa.biz;

import com.aaa.entity.MyUserInfo;
import com.aaa.entity.Role;
import com.aaa.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 陈建
 * @Date: 2020/5/22 0022 15:47
 * @Version 1.0
 * 用户相关的业务方法
 */
public interface UserBiz {

    PageInfo<User> selectAllUser(int page, int limit);
    User selectUserByUsername(String username);
    int insertSelective(User record);
    int delUserByID(List<String> ids);
    int updateByPrimaryKeySelective(User record);
    int resetPassword(User user);//重置密码
    int SoftdeleteByPrimaryKey(Integer id);//单行软删除
    int SoftdelUserByID( List<String> ids);//多行软删除
    List<MyUserInfo> selectByPhoneandLoginName(String phonenumber, String loginName);//查询条件
    int insertUserRole(Integer userId, Role role);//插入中间表
    int selectStatus(String loginname);//查状态
}
