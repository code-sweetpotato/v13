package com.qf.v13.base;

import java.util.List;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {


    public int deleteByPrimaryKey(Long id) {
        return getDao().deleteByPrimaryKey(id);
    }


    public int insert(T record) {
        return getDao().insert(record);
    }


    public int insertSelective(T record) {
        return getDao().insertSelective(record);
    }


    public T selectByPrimaryKey(Long id) {
        return (T) getDao().selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(T record) {
        return getDao().updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKeyWithBLOBs(T record) {


        return getDao().updateByPrimaryKeyWithBLOBs(record);

    }


    public int updateByPrimaryKey(T record) {
        return getDao().updateByPrimaryKey(record);
    }

    public List<T> list(){
        return getDao().list();
    };
}
