package com.flj.latte.btprint.list;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flj.latte.btprint.bluetooth.BluetoothPrintDelegate;
import com.flj.latte.btprint.detail.WaittingDelegate;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import static me.yokeyword.fragmentation.ISupportFragment.SINGLETOP;

public class AdapterItemClickListener implements BaseQuickAdapter.OnItemClickListener {
    private static String order_tag = null;
    private final LatteDelegate DELEGATE;

    private AdapterItemClickListener(LatteDelegate latteDelegate) {
        this.DELEGATE = latteDelegate;
    }

    public static AdapterItemClickListener create(LatteDelegate delegate, String tag) {
        order_tag = tag;
        return new AdapterItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultipleItemEntity multipleItemEntity = (MultipleItemEntity) adapter.getData().get(position);

        if (order_tag != null) {
            if (order_tag.equals("1")) {
                WaittingDelegate waittingDelegate = WaittingDelegate.create(multipleItemEntity);
                DELEGATE.getSupportDelegate().start(waittingDelegate, SINGLETOP);
            } else if (order_tag.equals("2")) {
                BluetoothPrintDelegate printDelegate = BluetoothPrintDelegate.create(multipleItemEntity, false);
                DELEGATE.getSupportDelegate().start(printDelegate, SINGLETOP);
            } else if (order_tag.equals("3")) {
                BluetoothPrintDelegate printDelegate = BluetoothPrintDelegate.create(multipleItemEntity, true);
                DELEGATE.getSupportDelegate().start(printDelegate, SINGLETOP);
            }
        }

    }
}
