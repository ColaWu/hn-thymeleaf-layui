package com.aaa.controller;

import com.aaa.biz.MenuBiz;
import com.aaa.biz.RoleBiz;
import com.aaa.entity.LayUiTable;
import com.aaa.entity.LayUiTree;
import com.aaa.entity.Role;
import com.aaa.entity.User;
import com.aaa.util.MyConstants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: 陈建
 * @Date: 2020/5/29 0029 16:27
 * @Version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleBiz roleBizImpl;
    @Autowired
    private MenuBiz menuBizImpl;
    @RequestMapping("/toShowRole")
    public String  toShowRole(){
        return "role/showRole";
    }

    @RequestMapping("/showRole")
    @ResponseBody
    public LayUiTable showRoleLayui() {
        //开始查询
        List<Role> roleList = roleBizImpl.selectAllRole();
        LayUiTable layUiTable = new LayUiTable();
        layUiTable.setCode(0);
        layUiTable.setMsg("返回消息");
        layUiTable.setData(roleList);
        return layUiTable;
    }
    //添加角色信息
    @RequestMapping("/saveRole")
    @ResponseBody
    public Object saveRole(Role role, Model model){
        Map map= new HashMap();
        if(roleBizImpl.selectByRoleName(role.getRoleName())!=null)
        {
            map.put("code",MyConstants.errorRoleCode);
            map.put("message",MyConstants.errorRoleMsg);
            return map;
        }
        else if(roleBizImpl.selectByRoleKey(role.getRoleKey())!=null)
        {
            map.put("code",MyConstants.errorKeyCode);
            map.put("message",MyConstants.errorKeyMsg);
            return map;
        }
        else
        {
            String creatperson=LoginController.person;
            role.setCreateBy(creatperson);
            role.setCreateTime(new Date());
            role.setRoleSort(roleBizImpl.selectLast().getRoleSort()+1);
            int i=roleBizImpl.insertSelective(role);
            if(i>0){
                map.put("code", MyConstants.successCode);
                map.put("message",MyConstants.saveSuccessMsg);
            }else {
                map.put("code",MyConstants.failCode);
                map.put("message",MyConstants.saveFailMsg);
            }
            return map;
        }
    }

    //编辑角色信息
    @RequestMapping("/editRole")
    @ResponseBody
    public Object editRole(Role role,Model model){
        Map map= new HashMap();
        if(roleBizImpl.selectByName(role.getRoleName(),role.getRoleId())!=null)
        {
            map.put("code",MyConstants.errorRoleCode);
            map.put("message",MyConstants.errorRoleMsg);
            return map;
        }
        else if(roleBizImpl.selectByKey(role.getRoleKey(),role.getRoleId())!=null)
        {
            map.put("code",MyConstants.errorKeyCode);
            map.put("message",MyConstants.errorKeyMsg);
            return map;
        }
        else
        {
            String updateperson=LoginController.person;
            role.setUpdateBy(updateperson);
            role.setUpdateTime(new Date());
            //int i = roleBizImpl.updateByRoleNameSelective(role);
            int i=roleBizImpl.updateByPrimaryKeySelective(role);
            if(i>0){
                map.put("code",MyConstants.successCode);
                map.put("message",MyConstants.editSuccessMsg);
            }else {
                map.put("code",MyConstants.failCode);
                map.put("message",MyConstants.editFailMsg);
            }
            return map;
        }
    }

    //删除角色信息
    @RequestMapping("/delRole")
    @ResponseBody
    public Object delRole( @RequestParam(value = "ids")String  ids){
        //将json字符串转换成list对象
        List<String> list= (List<String>) JSON.parse(ids);
        int i = roleBizImpl.SoftdelRoleByID(list);//软删除
        Map map= new HashMap();
        if(i>0){
            map.put("code",MyConstants.successCode);
            map.put("message",MyConstants.delSuccessMsg);
        }else {
            map.put("code",MyConstants.failCode);
            map.put("message",MyConstants.delFailMsg);
        }
        return map;
    }

    @RequestMapping("/toshow")
    @ResponseBody
    public LayUiTable toShow()
    {
        List<Role> roleList = roleBizImpl.selectRoles();
        LayUiTable layUiTable = new LayUiTable();
        layUiTable.setCode(0);
        layUiTable.setMsg("返回消息");
        layUiTable.setData(roleList);
        return layUiTable;
    }

    @RequestMapping("/selectAllMenu")
    @ResponseBody
    public List<LayUiTree> selectAllMenu(){
        List<LayUiTree> layUiTrees = menuBizImpl.selectAllMenu();
        return layUiTrees;
    }

    @RequestMapping(value="/setAuthorityByKey/{roleKey}",produces="application/json; utf-8")
    @ResponseBody
    public String setAuthorityByKey(String parms, @PathVariable String roleKey)
    {
        System.out.println("aaaa:"+parms.toString());


           List<LayUiTree> authorityTree=JSON.parseArray(parms,LayUiTree.class);
        System.out.println("----"+authorityTree.toString());
        List<String> stringList= returnTree(authorityTree);
        System.out.println("******"+stringList);
         return roleBizImpl.setAuthorityByKey(authorityTree,roleKey);

    }

    @RequestMapping("/setAuthorityById/{roleId}")
    @ResponseBody
    public String setAuthorityByKey(@RequestBody String parms, @PathVariable int roleId)
    {
        System.out.println(parms.toString());
        List<LayUiTree> authorityTree=JSON.parseArray(parms,LayUiTree.class);

        return roleBizImpl.setAuthorityById(authorityTree,Integer.valueOf(roleId));

    }
    public List<String>  returnTree(List<LayUiTree>authorityTree ){
        List<String> stringList =new ArrayList<>();
        for(LayUiTree layUiTree1:authorityTree){
            String s= layUiTree1.getTitle();
            stringList.add(s);
            if(layUiTree1.getChildren()!=null)
                return  returnTree(layUiTree1.getChildren());
        }
        return  stringList;
    }

}
