package com.flj.latte.app;

import com.flj.latte.util.storage.LattePreference;

import java.util.StringTokenizer;

/**
 * Created by gg on 2017/4/22
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }

    public static  void logout(){
        setSignState(false);
    }

    public static String[] getCustomerNames(){
        return LattePreference.getCustomer(LattePreference.USER_NAME);
    }

    public static String[] getCustomerPwds(){
        return LattePreference.getCustomer(LattePreference.USER_PWD);
    }

    public static String[] getCustomerID(){
        return LattePreference.getCustomer(LattePreference.USER_ID);
    }

    public static void add2Customers(String name,String pwd,String id){
        LattePreference.add2CutomerSet(name, pwd,id);
    }

    public static void setCurrentCustomer(String name,String pwd,String id){
        setCurrentCustomerName(name);
        setCurrentCustomerID(id);
    }

    public static void setCurrentCustomerID(String userid){
        LattePreference.addCustomAppProfile("userid", userid);
    }

    public static void setCurrentCustomerName(String name){
        LattePreference.addCustomAppProfile("username", name);
    }

    public static String getCurrentCustomerName(){
        return  LattePreference.getCustomAppProfile("username");
    }

    public static String getCurrentCustomerId(){
        return LattePreference.getCustomAppProfile("userid");
    }

//    public static String getCurrentCustomerName(){
//        String currentCustomer = null;
//        String[] split = currentCustomer.split(":");
//        return split[2];
//    }
}
