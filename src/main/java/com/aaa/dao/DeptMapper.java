package com.aaa.dao;

import com.aaa.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Repository
public interface DeptMapper {
    int deleteByPrimaryKey(Integer deptId);
    List<Dept> selectBydeptName(String deptName);
    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    List<Dept> selectAllDept();

    int delDeptByID(@Param("ids") List<String> ids);
    List<?> selectBydeptNameandcreate_by(@Param("deptName") String dept_name,@Param("createBy") String create_by);
}