package com.qf.v13.api;

import com.github.pagehelper.PageInfo;
import com.qf.v13.base.IBaseService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.ProductVO;

import java.util.List;

public interface IProductService extends IBaseService<TProduct> {
    public PageInfo getPage(int pageNum, int pageSize);

    //返回主键
    public long save(ProductVO vo);

    public int batchDel(List<Long> ids);

    public List<TProduct> batchSelectById(List<Long> ids);


}
