package com.flj.latte.bluetooth.bt;

import android.content.Intent;

/**
 * Created by gg on 8/1/17.
 */
public interface BtInterface {
    /**
     * start discovery bt device
     *
     */
    void btStartDiscovery(Intent intent);

    /**
     * finish discovery bt device
     *
     */
    void btFinishDiscovery(Intent intent);

    /**
     * bluetooth status changed
     *
     */
    void btStatusChanged(Intent intent);

    /**
     * found bt device
     *
     */
    void btFoundDevice(Intent intent);

    /**
     * device bond status change
     *
     */
    void btBondStatusChange(Intent intent);

    /**
     * pairing bluetooth request
     *
     */
    void btPairingRequest(Intent intent);
}
