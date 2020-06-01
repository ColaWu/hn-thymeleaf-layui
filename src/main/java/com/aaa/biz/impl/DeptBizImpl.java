package com.aaa.biz.impl;

import com.aaa.biz.DeptBiz;
import com.aaa.dao.DeptMapper;
import com.aaa.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 陈建
 * @Date: 2020/5/29 0029 16:49
 * @Version 1.0
 */
@Service
public class DeptBizImpl implements DeptBiz {
    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> selectAllDept() {
        return deptMapper.selectAllDept();
    }

    @Override
    public int insertSelective(Dept dept) {
        return deptMapper.insertSelective(dept);
    }


    @Override
    public int delDeptByID(List<String> ids) {
        return deptMapper.delDeptByID(ids);
    }

    @Override
    public int updateByPrimaryKeySelective(Dept dept) {
        return deptMapper.updateByPrimaryKeySelective(dept);
    }

    @Override
    public List<Dept> selectBydeptNameandcreate_by(String dept_name, String create_by) {
        return (List<Dept>)deptMapper.selectBydeptNameandcreate_by( dept_name,create_by);
    }

}
