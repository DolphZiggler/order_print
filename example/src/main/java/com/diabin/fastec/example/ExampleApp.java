package com.diabin.fastec.example;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.flj.latte.app.Latte;
import com.flj.latte.bluetooth.AppInfo;
import com.flj.latte.btprint.utils.PushUtils;
import com.flj.latte.net.rx.AddCookieInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by gg on 2017/3/29
 */
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withLoaderDelayed(1000)
                .withApiHost("http://192.168.31.80:20002/api/")
//                .withInterceptor(new DebugInterceptor("test", R.raw.test))
//                .withWeChatAppId("你的微信AppKey")
//                .withWeChatAppSecret("你的微信AppSecret")
//                .withJavascriptInterface("latte")
//                .withWebEvent("test", new TestEvent())
//                .withWebEvent("share", new ShareEvent())
//                添加Cookie同步拦截器
//                .withWebHost("https://www.baidu.com/")
                .withInterceptor(new AddCookieInterceptor())
                .configure();

        AppInfo.init(getApplicationContext());
        //初始化推送
        PushUtils.pushInit(this);
    }


    /**
     * 4.4适配
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
