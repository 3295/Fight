package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    int saveUser(User user);

    int saveUser2(User user);

    Map getCityInfo(String districtName,String cityName,String provinceName);

    Map getCityInfo2(String name);

    List<Map> getCityInfo3(String province,String city,String name);

    void updateAdCode(Object id,String code);
}
