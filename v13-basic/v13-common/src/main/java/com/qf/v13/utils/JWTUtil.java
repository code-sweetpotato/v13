package com.qf.v13.utils;


import com.qf.v13.entity.TUser;
import com.qf.v13.pojo.JWTResultBean;
import com.qf.v13.pojo.ResultBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

public class JWTUtil {
    //以秒为单位的时间
    public static String produceToken(String uuid,String subject){
        JwtBuilder jwtBuilder = Jwts.builder().setId(uuid)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .claim("role", "admin")
                .setExpiration(new Date(new Date().getTime() + 1000 * 30*60))
                .signWith(SignatureAlgorithm.HS256,"javaee1902");//设置算法和私钥

        return jwtBuilder.compact();

    }
    //出现异常跳转至登录界面,未出现异常重新生成token并返回
    public static ResultBean parseToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey("javaee1902")
                    .parseClaimsJws(token)
                    .getBody();
            String uuid = claims.getId();
            String subject = claims.getSubject();
            //重新生成token,以刷新有效期
            String newToken = produceToken(uuid, subject);
            Cookie cookie = new Cookie("user_uuid",newToken);
            //返回一个对象
            TUser user = new TUser();
            user.setId(Long.parseLong(uuid));
            user.setUsername(claims.getSubject());


            return new ResultBean("200",new JWTResultBean(newToken,user));


        }catch (Exception e){

            return new ResultBean("404","token过期或者未登陆");
        }

    }
}
