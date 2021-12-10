package com.sesi.studyunity2_back.demo.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface dao {
    
    @Select("select *from city limit 2 ")
    public List<Map<String,Object>>selectAll();
}
