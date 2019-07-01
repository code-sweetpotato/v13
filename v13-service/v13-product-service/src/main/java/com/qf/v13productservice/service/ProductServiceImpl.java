package com.qf.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.base.BaseServiceImpl;
import com.qf.v13.base.IBaseDao;


import com.qf.v13.entity.TProduct;

import com.qf.v13.entity.TProductDesc;
import com.qf.v13.mapper.TProductDescMapper;
import com.qf.v13.mapper.TProductMapper;
import com.qf.v13.pojo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService{


    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao getDao() {
        return productMapper;
    }

    @Override
    public PageInfo getPage(int pageNum,int pageSize){
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        //得到集合
        List<TProduct> list = productMapper.list();
        //将list装成pageinfo对象
        PageInfo pageInfo = new PageInfo(list,3);
        return pageInfo;

    }

    @Override
    //返回增加的主键
    @Transactional
    //事务控制的注解  增加
    public long save(ProductVO vo) {
        //保存商品的基本信息
        TProduct product = vo.getProduct();
        product.setFlag(true);
        productMapper.insert(product);
        //保存商品的描述信息
        String desc = vo.getProductDesc();
        TProductDesc productDesc = new TProductDesc();
        productDesc.seT(desc);
        productDesc.setProductId(product.getId());//新增后要主键回填

        productDescMapper.insert(productDesc);
        //返回新增商品的主键
        return product.getId();
    }

    @Override
    public int batchDel(List<Long> ids) {

        return productMapper.detchUpdateFlagById(ids);
    }

    @Override
    public List<TProduct> batchSelectById(List<Long> ids) {
        return productMapper.batchSelectById(ids);
    }

    /*
    * 方法重写,父方法是直接删除,而我们只是逻辑删除
    * */
    @Override
    public int deleteByPrimaryKey(Long id) {
        TProduct proProduct = new TProduct();
        proProduct.setId(id);
        proProduct.setFlag(false);
        return productMapper.updateByPrimaryKeySelective(proProduct);

    }
}
