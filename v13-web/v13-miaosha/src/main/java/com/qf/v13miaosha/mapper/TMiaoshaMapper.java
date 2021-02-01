package com.qf.v13miaosha.mapper;

import com.qf.v13miaosha.entity.TMiaosha;

import java.util.List;


public interface TMiaoshaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TMiaosha record);

    int insertSelective(TMiaosha record);

    TMiaosha selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TMiaosha record);

    int updateByPrimaryKey(TMiaosha record);

    List<TMiaosha> getCanStart();

    List<TMiaosha> getCanEnd();
}