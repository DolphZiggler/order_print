package com.flj.latte.btprint.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flj.latte.bluetooth.AppInfo;
import com.flj.latte.bluetooth.bt.BluetoothDelegate;
import com.flj.latte.bluetooth.print.PrintUtil;
import com.flj.latte.btprint.R;
import com.flj.latte.btprint.bean.OrderBean;
import com.flj.latte.btprint.index.IndexDelegate;
import com.flj.latte.btprint.utils.Constants;
import com.flj.latte.btprint.utils.ConvertUtils;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.Set;

import me.yokeyword.fragmentation.ISupportFragment;


/**
 * @author alan
 * Date  2018/6/7.
 * Function :蓝牙打印
 * Issue :
 */

public class BluetoothPrintDelegate extends BluetoothDelegate implements View.OnClickListener {
    BluetoothAdapter mAdapter = null;
    private int PERMISSION_REQUEST_COARSE_LOCATION = 2;
    TextView mTvBlueTooth = null;
    private String printString = null;
    private OrderBean orderBean = null;
    private static boolean isDone = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //6.0以上的手机要地理位置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        orderBean = ConvertUtils.getOrderInDelegate(this);
        printString = ConvertUtils.orderBean2String(orderBean, false, isDone);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_btprint;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mTvBlueTooth = rootView.findViewById(R.id.tv_blue_tooth);
        BluetoothController.init(this);
        $(R.id.bt_print).setOnClickListener(this);
        $(R.id.bt_choose).setOnClickListener(this);
        ((TextView) $(R.id.tv_order_info)).setText(ConvertUtils.convertText(printString));
    }

    public static BluetoothPrintDelegate create(MultipleItemEntity itemEntity, boolean isDone) {
        BluetoothPrintDelegate.isDone = isDone;
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ARG_ORDER_BEAN_PRINT, itemEntity);
        BluetoothPrintDelegate printDelegate = new BluetoothPrintDelegate();
        printDelegate.setArguments(bundle);
        return printDelegate;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_print) {
            if (TextUtils.isEmpty(AppInfo.btAddress)) {
                Toast.makeText(getContext(), "请先选择蓝牙...", Toast.LENGTH_SHORT).show();
            } else {
                if (mAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    //蓝牙被关闭时强制打开
                    mAdapter.enable();
                    Toast.makeText(getContext(), "蓝牙被关闭请打开...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "开始打印...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), BtService.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", orderBean);
                    intent.putExtras(bundle);
                    intent.setAction(PrintUtil.ACTION_PRINT_TEST);
                    getContext().startService(intent);
                }
            }
        } else if (id == R.id.bt_choose) {
            SearchBluetoothDelegate searchBluetoothDelegate = SearchBluetoothDelegate.create(orderBean);
            getSupportDelegate().start(searchBluetoothDelegate, SINGLETASK);
        }
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        String bluetoothDevice = args.getString("bluetoothDevice");
        mTvBlueTooth.setText("已绑定蓝牙：" + bluetoothDevice);
    }

    /**
     * 蓝牙状态改变时调用
     *
     * @param intent
     */
    @Override
    public void btStatusChanged(Intent intent) {
        super.btStatusChanged(intent);
        BluetoothController.init(this);
    }

}
