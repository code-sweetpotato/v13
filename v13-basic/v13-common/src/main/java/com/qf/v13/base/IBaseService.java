package com.qf.v13.base;

import java.util.List;

public interface IBaseService<T> {

    //每个子类告诉dao层
    public abstract IBaseDao getDao();

    //根据主键删除对象
    int deleteByPrimaryKey(Long id);

    //储存对象
    int insert(T record);

    //灵活储存对象
    int insertSelective(T record);

    //根据主键选择
    T selectByPrimaryKey(Long id);

    //灵活储存更新对象
    int updateByPrimaryKeySelective(T record);


    int updateByPrimaryKeyWithBLOBs(T record);

    int updateByPrimaryKey(T record);

    List<T> list();
}
