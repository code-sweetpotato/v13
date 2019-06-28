package com.qf.v13userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.IUserService;
import com.qf.v13.base.BaseServiceImpl;
import com.qf.v13.base.IBaseDao;
import com.qf.v13.entity.TUser;
import com.qf.v13.mapper.TUserMapper;
import com.qf.v13.pojo.ResultBean;
import com.qf.v13.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService{

    @Autowired
    private TUserMapper userMapper;

    @Resource(name = "redis")
    private RedisTemplate<String,TUser> redisTemplate;

    @Override
    public IBaseDao getDao() {
        return userMapper;
    }

    @Override
    public int isExistByUsername(String username) {
        return userMapper.isExistByUsername(username);

    }

    @Override
    public int isExistEmail(String email) {

        return userMapper.isExistEmail(email);
    }

    @Override
    public ResultBean selectByUsername(TUser user) {
        /*
        *1.从mysql数据库找出用户信息,只有username和password,方法参数只要username
        * 2.判断用户名和密码是否一致
        * 3.将用户信息(不包含密码保存至redis库中),设置有效期
        * 4.返回数据  成功后resultbean中包含uuid
        * */
        TUser resultUser = userMapper.selectByUsername(user.getUsername());
        if(resultUser != null){
            if(resultUser.getPassword().equals(user.getPassword())){
               String uuid = UUID.randomUUID().toString();
               String subject = "登录认证";
                String token = JWTUtil.produceToken(uuid, subject);
    /*                String key = "user_token_"+uuid;
                user.setPassword(null);
                redisTemplate.opsForValue().set(key,user);
                //设置有效期
                redisTemplate.expire(key,30, TimeUnit.MINUTES);*/



                return new ResultBean("200",token);
            }
        }

        return new ResultBean("404","");

    }

    @Override
    public ResultBean checkLogin(String uuid) {
   /*     String key = new StringBuffer("user_token_").append(uuid).toString();
        TUser user = redisTemplate.opsForValue().get(key);
        if(user !=null){
            redisTemplate.expire("user_token_"+uuid,30,TimeUnit.MINUTES);
            return new ResultBean("200",user);
        }
        return new ResultBean("404",null);*/
        return JWTUtil.parseToken(uuid);
    }

    @Override
    public TUser getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public boolean logout(String uuid) {
        //删除redis里面的数据
        String key = new StringBuffer("user_token_").append(uuid).toString();

        return redisTemplate.delete(key);
    }
}
