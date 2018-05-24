package com.example.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blueteam.common.api.GoodsService;
import com.blueteam.common.dto.GoodsDTO;
import com.blueteam.common.entity.Goods;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.jc.tools.CalendarUtil;
import com.jc.tools.HttpClientUtil;
import freemarker.template.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final static Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private HttpServletRequest request;

    @Resource
    private UserService userService;

    @Reference
    private GoodsService goodsService;

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
    public String helloWord() throws Exception{
        Date date= CalendarUtil.parse("2017-05-24 15:04:42");
        List<GoodsDTO> good=goodsService.getDetailListByUpdateTime(date);
        System.out.println(good.size()+"==>"+JSONObject.toJSON(good));
//        List<Goods> list=goodsService.getPageGoods(1,9);
//        for (Goods goods:list) {
//            System.out.println("==>"+JSONObject.toJSON(goods));
//        }
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

    String provinceName=null;
    String cityName=null;
    String districtName=null;
    List<String> list=new ArrayList<>();

    @ResponseBody
    @RequestMapping("/area")
    public String area() {
        String res=HttpClientUtil.httpRequest("http://restapi.amap.com/v3/config/district?key=0a39ea1e1dbe74927df23cd5b16e4e39&subdistrict=3");
        JSONObject json= JSON.parseObject(res);
        JSONArray jsonArray=json.getJSONArray("districts");
        json= JSON.parseObject(jsonArray.get(0).toString());
        jsonArray=json.getJSONArray("districts");
        recursive(jsonArray);
        for (String s:list) {
            System.out.println(s);
        }
        System.out.println("===>"+list.size());
        return "success";
    }



    public void recursive(JSONArray jsonArray){
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jb=JSONObject.parseObject(jsonArray.get(i).toString());
            String level=jb.getString("level");
            String name=jb.getString("name");
            if (level.equals("province")){
                provinceName=name;
                cityName=null;
                districtName=null;
            }else if (level.equals("city")){
                cityName = name;
                districtName = null;
//                if (name.equals("重庆郊县")) cityName=null;
            }else if (level.equals("district")) {
                districtName = name;
            }

//            if (userService.getCityInfo2(name)==null){
//                if (!level.equals("street")) {
//                    list.add(level + ":" + provinceName + "--" + cityName + "--" + districtName + "--(" + name + ")");
//                }
//            }

            List<Map> maps=userService.getCityInfo3(provinceName,cityName,name);
            if (maps==null||maps.size()==0||maps.size()>1){
                if (!level.equals("street")) {
                    list.add(level + ":" + provinceName + "--" + cityName + "--" + districtName + "--(" + name + ")");
                }
            }else {
                System.out.println(maps.get(0).get("Id")+"<=====>"+jb.getString("adcode"));
                userService.updateAdCode(maps.get(0).get("Id"),jb.getString("adcode"));
            }

            JSONArray array=jb.getJSONArray("districts");
            if (array!=null&&array.size()>0){
                recursive(array);
            }
        }
    }
}
