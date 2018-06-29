package com.flj.latte.btprint.bluetooth;

import com.flj.latte.btprint.bean.OrderBean;

import java.util.List;

/**
 * Print
 * * Created by liugruirong on 2017/8/3.
 */

public interface PrintDataMaker {
    List<byte[]> getPrintData(int type, OrderBean orderBean);
}
