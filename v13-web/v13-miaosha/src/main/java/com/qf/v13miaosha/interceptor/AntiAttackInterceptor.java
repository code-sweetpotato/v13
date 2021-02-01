package com.qf.v13miaosha.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class AntiAttackInterceptor implements HandlerInterceptor {


    /*
    * 利用拦截器对防刷攻击
    * 一分钟内访问次数超过3次警告
    * 超过六次加入黑名单
    * 禁止ip地址
    * */
    @Autowired
    private RedisTemplate<String,Object> redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();//访问用户的ip

        response.setContentType("text/html;charset=utf-8;");

        String userKey = new StringBuilder("miaosha:user:").append(ip).toString();

        String blackListKey = "blackList";

        Long viewCount = redis.opsForValue().increment(userKey);
        //判断访问ip是否在黑名单中
        Boolean isExist = redis.opsForSet().isMember(blackListKey, ip);
        if(isExist){
            //存在
            response.getWriter().write("你已被锁定,请申请解除");
            response.getWriter().flush();
            return false;
        }

        if(viewCount == 1){
            //第一次访问,对其设置时间
            redis.expire(userKey,60, TimeUnit.SECONDS);
            return true;

        }

        if(viewCount > 6){
            //将其加入黑名单
            redis.opsForSet().add(blackListKey,ip);
            //给提示
            response.getWriter().write("你已加入黑名单!");
            response.getWriter().flush();
            return false;
        }

        if(viewCount > 3){
            //警告他
            response.getWriter().write("请不要过于频繁访问!");
            response.getWriter().flush();
            return false;
        }

        return true;
    }
}
