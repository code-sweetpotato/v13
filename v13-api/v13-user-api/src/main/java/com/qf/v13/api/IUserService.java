package com.qf.v13.api;

import com.qf.v13.base.IBaseService;
import com.qf.v13.entity.TUser;
import com.qf.v13.pojo.ResultBean;

import javax.servlet.http.HttpServletResponse;

public interface IUserService extends IBaseService<TUser>{
    //通过用户名查询用户信息,返回若存在返回用户的id,不存在返回0
    public int isExistByUsername(String username);


    //判断注册邮箱是否存在
    public int isExistEmail(String email);

    //通过用户名找到用户,只需要用户的密码和用户名
    public ResultBean selectByUsername(TUser user);

    public ResultBean checkLogin(String uuid);


    public TUser getUserByUsername(String username);


    //账户注销
    public boolean logout(String uuid);



}
