package com.qf.v13.mapper;

import com.qf.v13.base.IBaseDao;
import com.qf.v13.entity.TProduct;

import java.util.List;

public interface TProductMapper extends IBaseDao<TProduct>{

    public int detchUpdateFlagById(List<Long> ids);

    public List<TProduct> batchSelectById(List<Long> ids);
}