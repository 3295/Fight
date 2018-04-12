package com.example.demo.dao.second;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondUserMapper {
    @Insert("insert into user (created_time, modifed_time,user_name, user_sex) values (now(),now(),#{userName}, #{userSex})")
    int insert(User record);
}
