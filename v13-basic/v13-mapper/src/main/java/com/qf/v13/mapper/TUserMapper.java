package com.qf.v13.mapper;

import com.qf.v13.base.IBaseDao;
import com.qf.v13.entity.TUser;
import org.apache.ibatis.annotations.Param;

public interface TUserMapper extends IBaseDao<TUser>{
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    public int isExistByUsername(String username) ;

    public int isExistEmail(String email);

    public TUser selectByUsername(String username);

    public TUser checkLogin(@Param("username") String username,@Param("password") String password);

    public TUser getUserByUsername(String username);
}