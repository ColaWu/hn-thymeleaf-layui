package com.aaa.controller;

import com.aaa.biz.DeptBiz;
import com.aaa.entity.Dept;
import com.aaa.entity.LayUiTable;
import com.aaa.entity.MyUserInfo;
import com.aaa.entity.User;
import com.aaa.util.MyConstants;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈建
 * @Date: 2020/5/29 0029 16:27
 * @Version 1.0
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptBiz deptBizImpl;
    @RequestMapping("/toShowDept")
    public String  toShowDept(){
        return "dept/showDept";
    }
    @RequestMapping("/toShowDept1")
    public String  toShowDept1(){
        return "dept/showDept1";
    }

    @RequestMapping("/showDept")
    @ResponseBody
    public LayUiTable showDept() {
        //开始查询
        List<Dept> deptList = deptBizImpl.selectAllDept();
        LayUiTable layUiTable = new LayUiTable();
        layUiTable.setCode(0);
        layUiTable.setMsg("返回消息");
        layUiTable.setData(deptList);
        return layUiTable;
    }
    @RequestMapping("/delDept")
    @ResponseBody
    public Object delUser( @RequestParam(value = "ids") String  ids){
        //将json字符串转换成list对象
        List<String> list= (List<String>) JSON.parse(ids);
        System.out.println(list.toString());
        int i = deptBizImpl.delDeptByID(list);
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
    @RequestMapping("/saveDept")
    @ResponseBody
    public Object saveUser(Dept d){
        String creatperson=LoginController.person;
        Date date=new Date();//将创建日期（当前时间创建进去）
        d.setStatus("0");
        d.setCreateTime(date);
        d.setCreateBy(creatperson);
        d.setUpdateTime(date);
        d.setUpdateBy(creatperson);
        int i = deptBizImpl.insertSelective(d);
        Map map= new HashMap<>();
        if(i>0){
            map.put("code", MyConstants.successCode);
            map.put("message",MyConstants.saveSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.saveFailMsg);
        }
        return map;
    }

    @RequestMapping("/selectAllDept")
    @ResponseBody
    public Object selectAllDept() {
        //开始查询
        List<Dept> deptList = deptBizImpl.selectAllDept();
        return deptList;
    }
    @RequestMapping("/editDept")
    @ResponseBody
    public Object editUser(Dept dept){

        Date date=new Date();
        dept.setUpdateTime(date);
        dept.setUpdateBy(LoginController.person);
        int i = deptBizImpl.updateByPrimaryKeySelective(dept);
        System.out.println(dept.toString());
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
    @RequestMapping("/selectBydeptNameandcreate_by")
    @ResponseBody
    public Object myUserInfos(String dept_name, String create_by) {
        //PageHelper.startPage(page,limit);
        System.out.println(dept_name+"---------"+create_by);
        List<Dept> userInfoList=deptBizImpl.selectBydeptNameandcreate_by(dept_name,create_by);
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
