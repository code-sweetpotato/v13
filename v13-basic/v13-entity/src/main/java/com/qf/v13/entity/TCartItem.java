package com.qf.v13.entity;

import java.io.Serializable;
import java.util.Date;

public class TCartItem implements Serializable,Comparable<TCartItem>{
    public TCartItem(Long prodact_id, Date create_time, int count) {
        this.prodact_id = prodact_id;
        this.create_time = create_time;
        this.count = count;
    }


    private Long serialVersionUID = 1L;

    public TCartItem() {
    }

    //商品id
    private Long prodact_id;
    //购物车创建时间
    private Date create_time;
    //商品的数量
    private int count;

    public Long getProdact_id() {
        return prodact_id;
    }

    public void setProdact_id(Long prodact_id) {
        this.prodact_id = prodact_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int compareTo(TCartItem o) {
        return (int) (o.getCreate_time().getTime()-this.getCreate_time().getTime());
    }



    @Override
    public String toString() {
        return "TCartItem{" +
                "prodact_id=" + prodact_id +
                ", create_time=" + create_time +
                ", count=" + count +
                '}';
    }
}
