package com.flj.latte.btprint.utils;

import android.content.Context;

import com.flj.latte.app.AccountManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dolph on 18-6-29.
 */

public class PushUtils {

    public static void pushInit(Context context) {
        JPushInterface.init(context);
        JPushInterface.setDebugMode(true);
        initTags(context);
    }


    public static void initTags(Context context) {
        String[] customerNames = AccountManager.getCustomerNames();
        Set<String> nameSet = new HashSet<>(Arrays.asList(customerNames));
        JPushInterface.addTags(context, 1, nameSet);
    }
}
