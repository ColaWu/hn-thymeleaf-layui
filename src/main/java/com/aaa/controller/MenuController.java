package com.aaa.controller;

import com.aaa.biz.MenuBiz;
import com.aaa.entity.*;

import com.aaa.util.MyConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈建
 * @Date: 2020/5/28 0028 6:59
 * @Version 1.0
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuBiz menuBiz;
    @RequestMapping("/toShowMenu")
    public String toShowMenu() {
        return "menu/showMenu";
    }
    @RequestMapping("/selectAllMenu")
    @ResponseBody
    public List<LayUiTree> selectAllMenu(){
        List<LayUiTree> layUiTrees = menuBiz.selectAllMenu();
        return layUiTrees;
    }
    @RequestMapping("/selectmenu")
    @ResponseBody
    public LayUiTreeTable selectMenu()
    {
        LayUiTreeTable layUiTrees=menuBiz.selectMenuByName();
        System.out.println(layUiTrees);
        return  layUiTrees;
    }
    @RequestMapping("/addMenu")
    @ResponseBody
    public Object saveUser(Menu menu){
        String creatperson=LoginController.person;
        Date date=new Date();
        menu.setCreateTime(date);
        menu.setCreateBy(creatperson);
        int i = menuBiz.insertSelective(menu);
        System.out.println(menu);
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

    @RequestMapping("/editMenu")
    @ResponseBody
    public Object editUser(Menu menu){
        String updateperson=LoginController.person;
        //System.out.println(updateperson);
        Date date=new Date();

        menu.setUpdateTime(date);
        menu.setUpdateBy(updateperson);
        int i = menuBiz.updateByPrimaryKeySelective(menu);
        Map map= new HashMap<>();
        map.put("code",MyConstants.successCode);
        map.put("message",MyConstants.editSuccessMsg);
        System.out.println("-----------"+i);
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.editSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.editFailMsg);
        }
        return map;
    }
    @RequestMapping("/selectMenu1")
    @ResponseBody
    public Object selectMenu1() {
        //开始查询
        List<Menu>menus= menuBiz.selectMenu1();
        System.out.println(menus);
        return menus;
    }

    @RequestMapping("/delMenu")
    @ResponseBody
    public Object delUser(Menu  m ){
        //将json字符串转换成list对象
        m.setVisible("1");
        Date date=new Date();
        m.setUpdateTime(date);
        m.setUpdateBy(LoginController.person);
        //int i = userBizImpl.delUserByID(list);
        int i=menuBiz.updateByPrimaryKeySelective(m);
        Map map= new HashMap<>();
        System.out.println("----------"+i);
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.delSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.delFailMsg);
        }
        return map;
    }



}
