package com.qf.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.base.BaseServiceImpl;
import com.qf.v13.base.IBaseDao;
import com.qf.v13.entity.TProductType;
import com.qf.v13.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService{

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Autowired
    private RedisTemplate<String,List<TProductType>> redisTemplate;

    @Override
    public IBaseDao getDao() {
        return productTypeMapper;
    }




    @Override
    public List<TProductType> list() {
        //先从redis中取商品类型,没有再去mysql数据库中去取
        List<TProductType> list = redisTemplate.opsForValue().get("productType");
        if(list == null){
            list = super.list();
            redisTemplate.opsForValue().set("productType",list);

        }
        return list;


    }
}
