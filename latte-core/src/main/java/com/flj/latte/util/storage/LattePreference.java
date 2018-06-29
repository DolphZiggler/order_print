package com.flj.latte.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.app.Latte;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gg on 2017/4/22
 */

public final class LattePreference {
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PWD = "USER_PWD";
    public static final String USER_ID = "USER_ID";

    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */
    private static Set<String> customerSet = new HashSet<>();

    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Latte.getApplicationContext());
    private static final String APP_PREFERENCES_KEY = "profile";

    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    public static void setAppProfile(String val) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCES_KEY, val)
                .apply();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCES_KEY, null);
    }

    public static JSONObject getAppProfileJson() {
        final String profile = getAppProfile();
        return JSON.parseObject(profile);
    }

    public static void removeAppProfile() {
        getAppPreference()
                .edit()
                .remove(APP_PREFERENCES_KEY)
                .apply();
    }

    public static void clearAppPreferences() {
        getAppPreference()
                .edit()
                .clear()
                .apply();
    }

    public static void setAppFlag(String key, boolean flag) {
        getAppPreference()
                .edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }

    public static void addCustomAppProfile(String key, String val) {
        getAppPreference()
                .edit()
                .putString(key, val)
                .apply();
    }

    public static String getCustomAppProfile(String key) {
        return getAppPreference().getString(key, "");
    }

    public static Set<String> getCustomerSet() {
        Set<String> userlist = getAppPreference().getStringSet("userlist", null);
        if (userlist == null) {
            userlist = new HashSet<>();
            getAppPreference().edit().putStringSet("userlist", userlist).commit();
        }
        return userlist;
    }

    public static String[] getCustomer(String option) {
        Set<String> customerSet = getCustomerSet();
        int index = 0;
        String[] infos = new String[customerSet.size()];
        for (String info : customerSet) {
            String[] split = info.split(":");
            if (USER_NAME.equals(option)) {
                infos[index++] = split[0];
            } else if (USER_PWD.equals(option)) {
                infos[index++] = split[1];
            }else  if(USER_ID.equals(option)){
                infos[index++] = split[2];
            }
        }
        return infos;
    }


    public static void add2CutomerSet(String name, String pwd,String id) {
        if (name != null && !name.isEmpty() && pwd != null && !pwd.isEmpty()&&id!=null&&!id.isEmpty())
            getCustomerSet().add(name + ":" + pwd+":"+id);
    }

}
