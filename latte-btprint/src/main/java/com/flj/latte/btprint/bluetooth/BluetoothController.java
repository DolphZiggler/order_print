package com.flj.latte.btprint.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.text.TextUtils;
import android.util.Log;

import com.flj.latte.bluetooth.print.PrintUtil;


/**
 * Created by gg on 8/1/17.
 */

public class BluetoothController {

    public static void init(BluetoothPrintDelegate delegate) {
        if (null == delegate.mAdapter) {
            delegate.mAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (null == delegate.mAdapter) {
            delegate.mTvBlueTooth.setText("该设备没有蓝牙模块");
            return;
        }
        Log.d("state","state");
        if (!delegate.mAdapter.isEnabled()) {

            if ( delegate.mAdapter.getState()== BluetoothAdapter.STATE_OFF ){
                //蓝牙被关闭时强制打开
                 delegate.mAdapter.enable();

            }else {
                delegate.mTvBlueTooth.setText("蓝牙未打开");
                return;
            }
        }
        String address = PrintUtil.getDefaultBluethoothDeviceAddress(delegate.getContext());
        if (TextUtils.isEmpty(address)) {
            delegate.mTvBlueTooth.setText("尚未绑定蓝牙设备");
            return;
        }
        String name = PrintUtil.getDefaultBluetoothDeviceName(delegate.getContext());
        delegate.mTvBlueTooth.setText("已绑定蓝牙：" + name );
    //    delegate.tv_blueadress.setText(address);

    }
    public static boolean turnOnBluetooth()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (bluetoothAdapter != null)
        {
            return bluetoothAdapter.enable();
        }
        return false;
    }
}
