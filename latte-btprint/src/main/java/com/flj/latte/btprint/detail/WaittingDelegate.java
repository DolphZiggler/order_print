package com.flj.latte.btprint.detail;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.app.AccountManager;
import com.flj.latte.app.Latte;
import com.flj.latte.btprint.R;
import com.flj.latte.btprint.bean.DeliveryMan;
import com.flj.latte.btprint.bean.OrderBean;
import com.flj.latte.btprint.index.IndexDelegate;
import com.flj.latte.btprint.list.OrderListDelegate;
import com.flj.latte.btprint.utils.Constants;
import com.flj.latte.btprint.utils.ConvertUtils;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.ui.recycler.MultipleItemEntity;


public class WaittingDelegate extends LatteDelegate implements View.OnClickListener {
    private OrderBean orderBean = null;
    private DeliveryMan[] deliveryMEN = null;
    private int mCurrentPosition = -1;
    private boolean selectedFlag = false;

    @Override
    public Object setLayout() {
        return R.layout.delegate_waitting;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadDeliveryList();
    }

    private void loadDeliveryList() {
        Latte.getHandler().postDelayed((
                        () -> RestClient.builder()
                                .url(Constants.DELIVERY_LIST)
                                .params("userid", AccountManager.getCurrentCustomerId())
                                .success(response -> {
                                    final JSONObject object = JSON.parseObject(response);
                                    initDeliverys(object);
                                })
                                .error((code, msg) -> Toast.makeText(Latte.getApplicationContext(), "请求错误：" + code + " " + msg, Toast.LENGTH_SHORT).show())
                                .failure(() -> Toast.makeText(Latte.getApplicationContext(), "网络故障，请求失败", Toast.LENGTH_SHORT).show())
                                .build()
                                .get())
                , 0);
    }

    private void initDeliverys(JSONObject object) {
        JSONArray data = object.getJSONArray("data");
        int size = data.size();
        deliveryMEN = new DeliveryMan[size];
        for (int i = 0; i < size; i++) {
            JSONObject obj = data.getJSONObject(i);
            String deliveryName = obj.getString("deliveryName");
            String deliveryId = obj.getString("deliveryId");
            deliveryMEN[i] = new DeliveryMan().setDeliveryId(deliveryId).setDeliveryName(deliveryName);
        }
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        orderBean = ConvertUtils.getOrderInDelegate(this);
        ((TextView) $(R.id.tv_order_waitting)).setText(ConvertUtils.convertText(ConvertUtils.orderBean2String(orderBean, true, false)));
        $(R.id.btn_post_deliveryman).setOnClickListener(this);
        $(R.id.tv_delivery_choose).setOnClickListener(this);
    }

    public static WaittingDelegate create(MultipleItemEntity itemEntity) {
        WaittingDelegate waittingDelegate = new WaittingDelegate();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ARG_ORDER_BEAN_PRINT, itemEntity);
        waittingDelegate.setArguments(bundle);
        return waittingDelegate;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_post_deliveryman) {
            if (selectedFlag)
                postDeliveryForOrder(mCurrentPosition);
            else Toast.makeText(_mActivity, "请选择配送员", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.tv_delivery_choose) {
            showPopWindow();
        }
    }

    private void showPopWindow() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.alert_content, null);
        ListView viewById = inflate.findViewById(R.id.alert_content);
        String[] adapterArray = new String[deliveryMEN.length];
        for (int i = 0; i < deliveryMEN.length; i++) {
            adapterArray[i] = deliveryMEN[i].getDeliveryName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, adapterArray);
        viewById.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage("选择配送员")
                .setView(inflate);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        viewById.setOnItemClickListener((parent, view, position, id) -> {
            ((TextView) $(R.id.tv_delivery_choose)).setText(deliveryMEN[position].getDeliveryName());
            mCurrentPosition = position;
            selectedFlag = true;
            alertDialog.dismiss();
        });
    }

    private void postDeliveryForOrder(int position) {
       /* OrderListDelegate orderListDelegate = new OrderListDelegate();
        Bundle bundle = new Bundle();
        bundle.putString("fresh", "fresh");
        orderListDelegate.setArguments(bundle);
        start(orderListDelegate,SINGLETASK);*/
        Latte.getHandler().postDelayed((
                        () -> RestClient.builder()
                                .url(Constants.DELIVERY_LIST)
                                .params("orderId", orderBean.getOrderId())
                                .params("deliveryName", deliveryMEN[position].getDeliveryId())
                                .success((response) -> {
                                    if ("1".equals(response)) {
                                        OrderListDelegate orderListDelegate = new OrderListDelegate();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("fresh", "fresh");
                                        orderListDelegate.setArguments(bundle);
                                        start(new IndexDelegate(), SINGLETASK);
                                    } else {
                                        Toast.makeText(_mActivity, response, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .error((code, msg) -> Toast.makeText(Latte.getApplicationContext(), "请求错误：" + code + " " + msg, Toast.LENGTH_SHORT).show())
                                .failure(() -> Toast.makeText(Latte.getApplicationContext(), "网络故障，请求失败", Toast.LENGTH_SHORT).show())
                                .build()
                                .post())
                , 0);
    }
}
