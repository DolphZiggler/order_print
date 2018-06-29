package com.flj.latte.btprint.bean;

import java.io.Serializable;

public class GoodsBean implements Serializable {
    //商家名称
    private String salerName;
    //商品名称
    private String goodsName;
    //商品规格

    private String spec;
    //商品数量
    private int count;
    //商品价格
    private double price;
    //商家电话
    private String salerphone;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //商家状态
    private String state;


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSalerName() {
        return salerName;
    }

    public String getSalerphone() {
        return salerphone;
    }

    public void setSalerphone(String salerphone) {
        this.salerphone = salerphone;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

}
