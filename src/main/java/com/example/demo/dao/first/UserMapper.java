package com.example.demo.dao.first;


import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insert(User record);


    User selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(User record);
}