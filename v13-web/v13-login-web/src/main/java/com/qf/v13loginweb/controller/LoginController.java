package com.qf.v13loginweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.v13.api.IUserService;
import com.qf.v13.entity.TUser;
import com.qf.v13.pojo.ResultBean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.HttpResource;


import javax.servlet.http.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("sso")
public class LoginController {

    @Reference
    private IUserService userService;




    @RequestMapping("login")
    @ResponseBody
    public ResultBean login(TUser user, HttpServletResponse response,String referer){
        ResultBean resultBean = userService.selectByUsername(user);
        if("200".equals(resultBean.getStatusCode())){
            String uuid = resultBean.getData().toString();//拿到uuid
            //将uuid储存到cookie上
            Cookie cookie = new Cookie("user_uuid",uuid);
            cookie.setPath("/");
            cookie.setDomain("qf.com");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            if("".equals(referer)){
                return new ResultBean("200","http://www.qf.com:9091/index/list");

            }else {
                return new ResultBean("200",referer);

            }

        }
        return new ResultBean("500","对不起!密码或者用户名不存在!");

     /*   if(user == null){
            return new ResultBean("500","对不起!密码或者用户名不存在!");
        }else {

            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("user_uuid",uuid);
            String key = "user_token_"+uuid;
            redisTemplate.opsForValue().set(key,user);
            //设置有效期
            redisTemplate.expire(key,30, TimeUnit.MINUTES);
            //将cookie保存,回应将cookie保存起来.
            response.addCookie(cookie);
            return new ResultBean("200","登录成功!");
        }*/


    }
    @RequestMapping("toLogin")
    public String toLogin(HttpServletRequest request,Model model){
        String referer = request.getHeader("referer");
        model.addAttribute("referer",referer);
        return "login";

    }

    @RequestMapping("checkLogin")
    @ResponseBody
    public ResultBean checkLogin(@CookieValue(name = "user_uuid",required = false) String uuid,
                                 HttpServletRequest request,String callBack){
     /*   Cookie[] cookies = request.getCookies();
        TUser user = null;
        for (Cookie cookie : cookies) {
            if("user_uuid".equals(cookie.getName())){
                //找到对应的cookie
                String uuid = cookie.getValue();
                user =(TUser) redisTemplate.opsForValue().get("user_token_" + uuid);
               // System.out.println("检查登录的接口的输出"+user.toString());
                break;
            }
        }
        if(user !=null){
            return "index";
        }else {
            return "login";
        }*/

        if(uuid != null){

            return userService.checkLogin(uuid);

        }

        return new ResultBean("404","uuid不存在");
    }

    @RequestMapping("logout")
    public String logout(@CookieValue(name = "user_uuid",required = false) String uuid,HttpServletResponse response){
        //注销功能   删除cookie和redis的数据
        if(uuid != null){
            /*boolean flag = userService.logout(uuid);
            if(flag){*/
                Cookie cookie = new Cookie("user_uuid",uuid);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setDomain("qf.com");
                response.addCookie(cookie);
                return "login";
            //}
        }
        return "index";

    }
     /*   Cookie[] cookies = request.getCookies();
        TUser user = null;
        for (Cookie cookie : cookies) {
            if("user_uuid".equals(cookie.getName())){
                //找到对应的cookie
                String uuid = cookie.getValue();

                redisTemplate.opsForValue().set("user_token_"+uuid,null);
                break;
            }
        }
        if(user !=null){
            return "index";
        }else {
            return "login";
        }
    }*/
     @RequestMapping("check")
     @ResponseBody
     public String check(@CookieValue(name = "user_uuid",required = false) String uuid,
                                   HttpServletRequest request, String callBack) throws JsonProcessingException {
        ResultBean resultBean = null;
         ObjectMapper objectMapper = new ObjectMapper();

         if(uuid != null){
             resultBean = userService.checkLogin(uuid);

             String string = objectMapper.writeValueAsString(resultBean);
             return callBack+"("+ string+")";

         }else {
             resultBean =new  ResultBean("404","uuid不存在");
             String s = objectMapper.writeValueAsString(resultBean);
             return callBack+"("+s+")";
         }


     }


    @RequestMapping("check2")
    @CrossOrigin(origins = "*",allowCredentials = "true")
    @ResponseBody
    public ResultBean check2(@CookieValue(name = "user_uuid",required = false) String uuid,
                        HttpServletRequest request, String callBack) throws JsonProcessingException {
        ResultBean resultBean = null;
        ObjectMapper objectMapper = new ObjectMapper();

        if(uuid != null){
           // resultBean = userService.checkLogin(uuid);
            resultBean = checkLogin(uuid, request, callBack);

            //return callBack+"("+ string+")";

        }else {
            resultBean =new  ResultBean("404","uuid不存在");

            //return callBack+"("+s+")";
        }
        return resultBean;

    }





}