package com.qf.v13.pojo;

import com.qf.v13.entity.TProduct;

import java.io.Serializable;

public class ProductVO implements Serializable {
    private TProduct product;
    //商品的描述
    private String productDesc;

    public TProduct getProduct() {
        return product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public ProductVO() {
    }
}
