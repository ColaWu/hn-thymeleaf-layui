package com.aaa.controller;

import com.aaa.biz.DeptBiz;
import com.aaa.biz.RoleBiz;
import com.aaa.biz.UserBiz;
import com.aaa.entity.*;
import com.aaa.util.MyConstants;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: 陈建
 * @Date: 2020/5/22 0022 15:32
 * @Version 1.0
 * 用户管理
 */
@Controller
//此处的RequestMapping是窄化请求，1年级
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBiz userBizImpl;
    @Autowired
    private DeptBiz deptBizImpl;//为了增加部门
    @Autowired
    private RoleBiz roleBizImpl;//为了增加角色


    @RequestMapping("/toShowUser")
    public String toShowUserLayui() {
        return "user/showUser";
    }

    @RequestMapping("/showUser")
    @ResponseBody
    public LayUiTable showUserLayui(int page, int limit) {
        //开始查询
        PageInfo<User> pageInfo = userBizImpl.selectAllUser(page, limit);
        LayUiTable layUiTable = new LayUiTable();
        layUiTable.setCode(0);
        layUiTable.setMsg("返回消息");
        //设置分页之后的返回值
        layUiTable.setCount(pageInfo.getTotal());
        layUiTable.setData(pageInfo.getList());
        return layUiTable;
    }
    @RequestMapping("/selectAllDept")
    @ResponseBody
    public Object selectAllDept() {
        //开始查询
        List<Dept>depts = deptBizImpl.selectAllDept();
        System.out.println(depts);

        return depts;
    }
    @RequestMapping("/selectAllRole")
    @ResponseBody
    public Object selectAllRole() {
        //开始查询
        List<Role>roles= roleBizImpl.selectAllRole();
        System.out.println(roles);

        return roles;
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public Object saveUser(User userInfo, HttpServletRequest request){

        String creatperson=LoginController.person;
        userInfo.setCreateBy(creatperson);
        int i = userBizImpl.insertSelective(userInfo);
        System.out.println(userInfo);
        String roleId=request.getParameter("select");//获取用户选中的角色id
        Integer roleid=Integer.parseInt(roleId);
        Integer userId=userInfo.getUserId();//获取该用户的id
        Role role=new Role();
        role.setRoleId(roleid);

        int a=userBizImpl.insertUserRole(userId,role);
        Map map= new HashMap<>();
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.saveSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.saveFailMsg);
        }
        return map;
    }

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    @RequestMapping("/editUser")
    @ResponseBody
    public Object editUser(User userInfo,Model model){
      /*  if(userInfo.status=="on")
        {
            userInfo.setStatus("0");
        }
        else if(userInfo.status=="off")
        {
            userInfo.setStatus("1");
        }*/
        String updateperson=LoginController.person;
        System.out.println(updateperson);
        userInfo.setUpdateBy(updateperson);
        int i = userBizImpl.updateByPrimaryKeySelective(userInfo);
        Map map= new HashMap<>();
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.editSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.editFailMsg);
        }
        return map;
    }
    @RequestMapping("/delUser")
    @ResponseBody
    public Object delUser( @RequestParam(value = "ids") String  ids){
        //将json字符串转换成list对象
        List<String> list= (List<String>) JSON.parse(ids);
        //int i = userBizImpl.delUserByID(list);
        int i = userBizImpl.SoftdelUserByID(list);//软删除
        Map map= new HashMap<>();
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.delSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.delFailMsg);
        }
        return map;
    }
    @RequestMapping("/updateUserPassword")
    @ResponseBody
    public Object updateUserPassword(@RequestBody User user)
    {
        int i=userBizImpl.resetPassword(user);

        Map map= new HashMap<>();
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.delSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.delFailMsg);
        }
        return map;
    }
    @RequestMapping("/selectPhoneandLogin_name")
    @ResponseBody
    public Object myUserInfos( String phonenumber, String loginName) {
        //PageHelper.startPage(page,limit);

        List<MyUserInfo> userInfoList=userBizImpl.selectByPhoneandLoginName(phonenumber,loginName);
        //PageInfo<MyUserInfo>pageInfo=new PageInfo(userInfoList);
        System.out.println(userInfoList);
        LayUiTable layUITable=new LayUiTable();
        layUITable.setCode(0);
        layUITable.setMsg("查询消息");
        layUITable.setCount(userInfoList.size());
        layUITable.setData(userInfoList);
//        layUITable.setCount(pageInfo.getTotal());
//        layUITable.setData(pageInfo.getList());
        return layUITable;
    }
}
