package com.flj.latte.btprint.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderBean implements Serializable {
    //订单唯一标识
    private String orderId;
    //订单编号
    private String orderNum;
    //备注
    private String remark;
    //状态
    private String status;


    //商品明细
    private List<GoodsBean> goodsBeans;


    //配送费
    private double sendFee;
    //优惠卷
    private double couponFee;
    //商品总费
    private double totalFee;
    //实付费
    private double paidFee;


    //送达时间
    private String arriveTime;
    //收货人
    private String receiverName;
    //收货人电话
    private String reveiverPhone;
    //收获地址
    private String receiverAddress;
    //送货员
    private String deliveryMan;
    //配送时间
    private String sendDate;

    public String getOrderNum() {
        return orderNum;
    }

    public OrderBean setOrderNum(String orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public OrderBean setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public List<GoodsBean> getGoodsBeans() {
        return goodsBeans;
    }

    public OrderBean setGoodsBeans(List<GoodsBean> goodsBeans) {
        this.goodsBeans = goodsBeans;
        return this;
    }

    public double getSendFee() {
        return sendFee;
    }

    public OrderBean setSendFee(double sendFee) {
        this.sendFee = sendFee;
        return this;
    }

    public double getCouponFee() {
        return couponFee;
    }

    public OrderBean setCouponFee(double couponFee) {
        this.couponFee = couponFee;
        return this;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public OrderBean setTotalFee(double totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public double getPaidFee() {
        return paidFee;
    }

    public OrderBean setPaidFee(double paidFee) {
        this.paidFee = paidFee;
        return this;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public OrderBean setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public OrderBean setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public String getReveiverPhone() {
        return reveiverPhone;
    }

    public OrderBean setReveiverPhone(String reveiverPhone) {
        this.reveiverPhone = reveiverPhone;
        return this;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public OrderBean setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public OrderBean setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
        return this;
    }

    public String getSendDate() {
        return sendDate;
    }

    public OrderBean setSendDate(String sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderBean setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderBean setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
}
