package com.flj.latte.btprint.list;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.flj.latte.btprint.bluetooth.BluetoothPrintDelegate;
import com.flj.latte.btprint.detail.WaittingDelegate;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import static me.yokeyword.fragmentation.ISupportFragment.SINGLETOP;

public class OrderItemClickListener extends SimpleClickListener {

    private static String order_tag = null;

    private final LatteDelegate DELEGATE;

    private OrderItemClickListener(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public static SimpleClickListener create(LatteDelegate delegate, String tag) {
        order_tag = tag;
        return new OrderItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItemEntity multipleItemEntity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);

        if (order_tag != null && order_tag.equals("1")) {
            WaittingDelegate waittingDelegate = WaittingDelegate.create(multipleItemEntity);
            DELEGATE.getSupportDelegate().start(waittingDelegate, SINGLETOP);
        } else {
            BluetoothPrintDelegate printDelegate = BluetoothPrintDelegate.create(multipleItemEntity, false);
            DELEGATE.getSupportDelegate().start(printDelegate, SINGLETOP);
        }

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
