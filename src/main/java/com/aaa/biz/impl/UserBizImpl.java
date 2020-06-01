package com.aaa.biz.impl;

import com.aaa.biz.UserBiz;
import com.aaa.dao.UserMapper;
import com.aaa.entity.MyUserInfo;
import com.aaa.entity.Role;
import com.aaa.entity.User;
import com.aaa.shiro.ShiroUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static com.aaa.shiro.ShiroUtil.encryptionBySalt;

/**
 * @Author: 陈建
 * @Date: 2020/5/22 0022 15:49
 * @Version 1.0
 * <p>
 * 请求=》controller=>service(biz)=>dao=>DB
 */
@Service
public class UserBizImpl implements UserBiz{
        @Autowired
        private UserMapper userMapper;
        @Override
        public PageInfo<User> selectAllUser(int page, int limit) {
            //开始分页,第一个参数是当前第几页，第二个参数是一页显示多少行
            PageHelper.startPage(page, limit);
            List<User> myUserInfos = userMapper.selectAllUser();
            //结束分页,pageInfo封装了分页之后所有数据
            PageInfo<User> pageInfo = new PageInfo(myUserInfos);
            return pageInfo;

        }

        /**
         * 按照用户名查询用户信息
         *
         * @param username
         * @return
         */
        @Override
        public User selectUserByUsername(String username) {

            return userMapper.selectUserByUsername(username);
        }

        @Override
        public int insertSelective(User record) {
            //次处要进行密码加盐加密

            Date date=new Date();//将创建日期（当前时间创建进去）
            record.setCreateTime(date);
            String salt = UUID.randomUUID().toString();
            String message = record.getPassword();
            String encryption = ShiroUtil.encryptionBySalt(salt, message);
            record.setPassword(encryption);
            record.setSalt(salt);
            return userMapper.insertSelective(record);
        }
    @Override
    public int resetPassword(User user)
    {
        Integer id= user.getUserId();

        User user1=userMapper.selectByPrimaryKey(id);
        String salt=user1.getSalt();
        String message=user.getPassword();

        System.out.println(salt);
        String s=encryptionBySalt(salt,message);
        System.out.println(s+"密码"+message);
        user.setPassword(s);
        return userMapper.resetPassword(user);
    }
        @Override
        public int delUserByID(List<String> ids) {
            return userMapper.delUserByID(ids);
        }

        @Override
        public int updateByPrimaryKeySelective(User record) {
            Date date=new Date();//将修改日期（当前时间创建进去）
            record.setUpdateTime(date);
            return userMapper.updateByPrimaryKeySelective(record);
        }

        @Override
        public int SoftdeleteByPrimaryKey(Integer id)
        {
            return userMapper.SoftdeleteByPrimaryKey(id);
        }
    //单行软删除
      @Override
      public int SoftdelUserByID( List<String> ids)
      {
          return userMapper.SoftdelUserByID(ids);
      }
    //多行软删除
    @Override
    public List<MyUserInfo> selectByPhoneandLoginName(String phonenumber, String loginName)
    {
        return userMapper.selectByPhoneandLoginName(phonenumber,loginName);
    }
    @Override
    public int insertUserRole(Integer userId, Role role)
    {
        return userMapper.insertUserRole(userId,role);
    }

    //插入中间表
    @Override
    public int selectStatus(String loginname)
    {
        return userMapper.selectStatus(loginname);
    }
}
