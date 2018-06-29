package com.flj.latte.btprint.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.btprint.bean.GoodsBean;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.ItemType;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            String orderId = data.getString("orderId");
            String orderNumer = data.getString("orderNumer");
            String deliveryman = data.getString("deliveryman");
            String mobile = data.getString("mobile");
            String site = data.getString("site");
            String receiveman = data.getString("receiveman");
            String forwardingTime = data.getString("forwardingTime");
            String sendTime = data.getString("sendTime");
            String status = data.getString("status");
            String sendPrice = data.getString("sendPrice");
            String goodPrice = data.getString("goodPrice");
            String paidPrice = data.getString("paidPrice");
            String coupon = data.getString("coupon");


            List<GoodsBean> goodsBeans = new ArrayList<>();
            JSONArray goods = data.getJSONArray("goods");
            int goodSize = goods.size();
            for (int j = 0; j < goodSize; j++) {
                GoodsBean goodsBean = new GoodsBean();
                JSONObject goodData = goods.getJSONObject(j);
                goodsBean.setSalerName(goodData.getString("saler"));
                goodsBean.setCount(goodData.getInteger("count"));
                goodsBean.setPrice(goodData.getDouble("price"));
                goodsBean.setSpec(goodData.getString("spec"));
                goodsBean.setGoodsName(goodData.getString("name"));
                goodsBean.setSalerphone(goodData.getString("salerphone"));
                goodsBean.setState(goodData.getString("state"));
                goodsBeans.add(goodsBean);
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.ORDER_LIST_ITEM)
                    .setField(MultipleFields.ORDER_orderId, orderId)
                    .setField(MultipleFields.ORDER_orderNumer, orderNumer)
                    .setField(MultipleFields.ORDER_deliveryman, deliveryman)
                    .setField(MultipleFields.ORDER_mobile, mobile)
                    .setField(MultipleFields.ORDER_site, site)
                    .setField(MultipleFields.ORDER_receiveman, receiveman)
                    .setField(MultipleFields.ORDER_forwardingTime, forwardingTime)
                    .setField(MultipleFields.ORDER_sendTime, sendTime)
                    .setField(MultipleFields.ORDER_sendPrice, sendPrice)
                    .setField(MultipleFields.ORDER_goodPrice, goodPrice)
                    .setField(MultipleFields.ORDER_paidPrice, paidPrice)
                    .setField(MultipleFields.ORDER_status, status)
                    .setField(MultipleFields.ORDER_goods, goodsBeans)
                    .setField(MultipleFields.ORDER_coupon, coupon)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}

