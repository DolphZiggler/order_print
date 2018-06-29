package com.flj.latte.btprint.bluetooth;

import android.content.Context;

import com.flj.latte.bluetooth.printutil.PrinterWriter;
import com.flj.latte.bluetooth.printutil.PrinterWriter58mm;
import com.flj.latte.bluetooth.printutil.PrinterWriter80mm;
import com.flj.latte.btprint.bean.GoodsBean;
import com.flj.latte.btprint.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 测试数据生成器
 * Created by gg on 8/1/17.
 */

public class PrintOrderDataMaker implements PrintDataMaker {


    private String qr;
    private int width;
    private int height;
    Context btService;


    public PrintOrderDataMaker(Context btService, String qr, int width, int height) {
        this.qr = qr;
        this.width = width;
        this.height = height;
        this.btService = btService;
    }

    @Override
    public List<byte[]> getPrintData(int type,OrderBean order) {
        ArrayList<byte[]> data = new ArrayList<>();

        try {
            PrinterWriter printer;
            printer = type == PrinterWriter58mm.TYPE_58 ? new PrinterWriter58mm(height, width) : new PrinterWriter80mm(height, width);
            printer.setAlignCenter();
            data.add(printer.getDataAndReset());

            printer.setAlignLeft();
            printer.printLine();
            printer.printLineFeed();
            printer.printLine();
            printer.printLineFeed();
            printer.setFontSize(1);
            printer.setAlignCenter();
            printer.print("源本圣逛市场");
            printer.printLineFeed();
            printer.setFontSize(0);
            printer.printLine();
            printer.printLineFeed();
            printer.printLine();
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("此小票仅限当日有效");
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("订单编号：" + order.getOrderNum());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("备注：" );
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("商品明细单");
            printer.printLineFeed();

            printer.setAlignLeft();
            printer.printLine();
            printer.printLineFeed();

            List<GoodsBean> wareList = order.getGoodsBeans();

            for (GoodsBean ware:wareList){
                printer.print(ware.getSalerName());
                printer.printLineFeed();
                printer.print("商品名称:"+ware.getGoodsName());
                printer.printLineFeed();
                printer.print("规格:"+ware.getSpec());
                printer.printLineFeed();
                printer.print("数量:"+ware.getCount()+" 价格:"+ware.getPrice());
                printer.printLineFeed();
            }

            printer.printLine();
            printer.printLineFeed();
            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("配送费:"+order.getSendFee());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("优惠劵 ："+order.getCouponFee());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("商品费：" +order.getTotalFee());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("实付费：" +order.getPaidFee());
            printer.printLineFeed();
            printer.printLine();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("送达时间：" +order.getArriveTime());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("收货人：" +order.getReceiverName());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("电话：" +order.getReveiverPhone());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("地址：" +order.getReceiverAddress());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("送货员：" +order.getDeliveryMan());
            printer.printLineFeed();

            printer.setFontSize(0);
            printer.setAlignLeft();
            printer.print("配送时间：" +order.getSendDate());
            printer.printLineFeed();

            printer.printLineFeed();
            printer.printLineFeed();
            printer.printLineFeed();
            printer.printLineFeed();

            data.add(printer.getDataAndClose());
            return data;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
