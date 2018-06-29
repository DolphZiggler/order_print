package com.flj.latte.btprint.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.flj.latte.btprint.bean.GoodsBean;
import com.flj.latte.btprint.bean.OrderBean;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

public class ConvertUtils {
    public static OrderBean getOrderInDelegate(LatteDelegate delegate) {
        MultipleItemEntity itemEntity = (MultipleItemEntity) delegate.getArguments().getSerializable(Constants.ARG_ORDER_BEAN_PRINT);
        return itemEntity2OrderBean(itemEntity);
    }

    public static SpannableString convertText(String source) {
        SpannableString spannable = new SpannableString(source);
        int start = source.indexOf("||");
        while (start > 0 && start < source.length()) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), start + 2, start + 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            start = source.indexOf("||", start + 3);
        }

        start = source.indexOf("TEL:");
        while (start > 0 && start < source.length()) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), start + 4, start + 15, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            start = source.indexOf("TEL:", start + 5);
        }
        return spannable;
    }

    public static OrderBean itemEntity2OrderBean(MultipleItemEntity itemEntity) {
        if (itemEntity != null) {
            return new OrderBean().setGoodsBeans(itemEntity.getField(MultipleFields.ORDER_goods))
                    .setOrderId(itemEntity.getField(MultipleFields.ORDER_orderId))
                    .setOrderNum(itemEntity.getField(MultipleFields.ORDER_orderNumer))
                    .setReveiverPhone(itemEntity.getField(MultipleFields.ORDER_mobile))
                    .setReceiverAddress(itemEntity.getField(MultipleFields.ORDER_site))
                    .setReceiverName(itemEntity.getField(MultipleFields.ORDER_receiveman))
                    .setSendFee(Double.parseDouble(itemEntity.getField(MultipleFields.ORDER_sendPrice)))
                    .setPaidFee(Double.parseDouble(itemEntity.getField(MultipleFields.ORDER_paidPrice)))
                    .setTotalFee(Double.parseDouble(itemEntity.getField(MultipleFields.ORDER_goodPrice)))
                    .setCouponFee(Double.parseDouble(itemEntity.getField(MultipleFields.ORDER_coupon)))
                    .setDeliveryMan(itemEntity.getField(MultipleFields.ORDER_deliveryman))
                    .setArriveTime(itemEntity.getField(MultipleFields.ORDER_forwardingTime))
                    .setSendDate(itemEntity.getField(MultipleFields.ORDER_sendTime));
        }
        return null;
    }

    public static String orderBean2String(OrderBean orderBean, boolean isWaitting, boolean isDone) {
        StringBuilder sb = new StringBuilder();
        if (orderBean != null) {
            sb.append("源本圣逛市场\n")
                    .append("此小票仅限当日有效\n")
                    .append("NO：" + orderBean.getOrderNum() + "\n")
                    .append("备注：\n")
                    .append("商品明细单\n")
                    .append("——————————————————————\n\n");
            List<GoodsBean> goodsBeans = orderBean.getGoodsBeans();
            int goodSize = goodsBeans.size();
            for (int i = 0; i < goodSize; i++) {
                GoodsBean goodsBean = goodsBeans.get(i);
                sb.append(goodsBean.getSalerName());
                if (isWaitting) {
                    sb.append("  ||" + goodsBean.getState());
                }
                if (!isDone) {
                    sb.append("   TEL:" + goodsBean.getSalerphone() + "\n");
                }
                sb.append("商品名称:" + goodsBean.getGoodsName() + "\n")
                        .append("规格:" + goodsBean.getSpec() + "\n")
                        .append("数量:" + goodsBean.getCount() + " 价格:" + goodsBean.getPrice() + "\n\n");
            }
            sb.append("——————————————————————\n")
                    .append("配送费: " + orderBean.getSendFee() + "元\n")
                    .append("优惠卷: " + orderBean.getCouponFee() + "\n")
                    .append("商品费: " + orderBean.getTotalFee() + "元\n")
                    .append("实付费: " + orderBean.getPaidFee() + "元\n")
                    .append("——————————————————————\n")
                    .append("送达时间: " + orderBean.getArriveTime() + "\n")
                    .append("收货人: " + orderBean.getReceiverName() + "\n")
                    .append("电话: " + orderBean.getReveiverPhone() + "\n");
            String address = orderBean.getReceiverAddress();
            if (address.length() > 15) {
                sb.append("地址: " + address.substring(0, 15) + "\n")
                        .append(address.substring(15) + "\n");
            } else {
                sb.append("地址: " + address + "\n");
            }
            if (!isWaitting) {
                sb.append("送货员: " + orderBean.getDeliveryMan() + "\n")
                        .append("配送时间: " + orderBean.getSendDate());
            }
        }
        return sb.toString();
    }
}
