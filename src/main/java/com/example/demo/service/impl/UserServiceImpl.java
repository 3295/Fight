package com.example.demo.service.impl;

import com.example.demo.configure.dynamicMultiDataSource.DS;
import com.example.demo.dao.first.UserMapper;
import com.example.demo.dao.second.SecondUserMapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService{

    private static final Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

//    @Resource
//    private SecondUserMapper secondUserMapper;//多数据源，分包实现的方式


    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    @DS("ds2")//多数据源，注解+AOP实现的方式
    @Override
    public int saveUser2(User user) {
        return userMapper.insert(user);
    }

    @Override
    public Map getCityInfo(String districtName,String cityName,String provinceName) {
        return userMapper.getCityInfo(districtName,cityName,provinceName);
    }

    @Override
    public Map getCityInfo2(String name) {
        return userMapper.getCityInfo2(name);
    }

    @Override
    public List<Map> getCityInfo3(String province,String city,String name) {
        return userMapper.getCityInfo3(province,city,name);
    }

    @Override
    public void updateAdCode(Object id, String code) {
        userMapper.updateAdCode(id, code);
    }
}
