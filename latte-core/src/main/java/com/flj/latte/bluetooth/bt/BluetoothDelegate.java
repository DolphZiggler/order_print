package com.flj.latte.bluetooth.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.flj.latte.delegates.LatteDelegate;


/**
 * Created by gg on 8/1/17.
 */
public abstract class BluetoothDelegate extends LatteDelegate implements BtInterface {


    /**
     * blue tooth broadcast receiver
     */
    protected BroadcastReceiver mBtReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == intent) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                btStartDiscovery(intent);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                btFinishDiscovery(intent);
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                btStatusChanged(intent);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                btFoundDevice(intent);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                btBondStatusChange(intent);
            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                btPairingRequest(intent);
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BtUtil.registerBluetoothReceiver(mBtReceiver, getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BtUtil.unregisterBluetoothReceiver(mBtReceiver, getActivity());
    }

    @Override
    public void btStartDiscovery(Intent intent) {
        System.out.println("wangzhen   StartDiscovery");
    }

    @Override
    public void btFinishDiscovery(Intent intent) {
        System.out.println("wangzhen   FinishDiscovery");
    }

    @Override
    public void btStatusChanged(Intent intent) {
        System.out.println("wangzhen   StatusChanged"+intent.getAction());
    }

    @Override
    public void btFoundDevice(Intent intent) {
        System.out.println("wangzhen   FoundDevice");
    }

    @Override
    public void btBondStatusChange(Intent intent) {
        System.out.println("wangzhen   BondStatusChange");
    }

    @Override
    public void btPairingRequest(Intent intent) {
        System.out.println("wangzhen   PairingRequest");
    }
}
