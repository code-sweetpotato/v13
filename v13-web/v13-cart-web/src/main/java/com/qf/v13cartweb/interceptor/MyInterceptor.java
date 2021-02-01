package com.qf.v13cartweb.interceptor;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IUserService;
import com.qf.v13.pojo.JWTResultBean;
import com.qf.v13.pojo.ResultBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class MyInterceptor implements HandlerInterceptor{

    @Reference
    private IUserService userService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();


        if(cookies ==null&&cookies.length==0){
            return true;
        }

        for (Cookie c : cookies) {
            if("user_uuid".equals(c.getName())){
                if(c.getValue() != null||"".equals(c.getValue())){
                    //调用检查登录的接口,并在user_cart的cookie值改为用户的i值
                    ResultBean resultBean = userService.checkLogin(c.getValue());
                    if("200".equals(resultBean.getStatusCode())){
                        JWTResultBean result = (JWTResultBean) resultBean.getData();
                        request.setAttribute("user",result.getUser());


                    }

                    return true;
                }
            }

        }
        return true;
    }
}
