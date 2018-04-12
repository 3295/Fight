package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final static Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private HttpServletRequest request;

    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public String hello() {
        request.setAttribute("name","spring-boot");
        saveVisitor(request);
        new Thread(new Runnable(){
            @Override
            public void run() {
                int i=0/0;
                log.info("========>run able");
            }
        }).start();
        log.info("========>run able finish");
        return "index";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String helloWord() {
        return "hello world";
    }


    @ResponseBody
    @RequestMapping("/saveUser")
    public String saveUser() {
        //int i=1/0;
        User user=new User();
        user.setUserName("第一个用户1");
        Integer sex=1;
        user.setUserSex(sex);
        int res=userService.saveUser(user)+userService.saveUser2(user);
        if (res>0)
            return "success,一共插入了"+res+"条数据";
        return "failed";
    }

    private void saveVisitor(HttpServletRequest request){
        String ip = request.getHeader("X-forwarded-for");
        System.out.println("=======>"+ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        System.out.println("=======>"+ip);
    }
}
