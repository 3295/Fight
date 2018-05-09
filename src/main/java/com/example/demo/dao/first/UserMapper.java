package com.example.demo.dao.first;


import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    int insert(User record);


    User selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(User record);

    Map getCityInfo(@Param("districtName") String districtName,@Param("cityName") String cityName,@Param("provinceName") String provinceName);
    Map getCityInfo2(String name);

    List<Map> getCityInfo3(@Param("province") String province,@Param("city") String city,@Param("name") String name);

    int updateAdCode(@Param("id") Object id,@Param("adcode") String code);
}