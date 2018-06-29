package com.flj.latte.btprint.sign;

import com.flj.latte.app.AccountManager;

/**
 * Created by gg on 2017/4/22
 */

public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {
        /* final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");
        Toast.makeText(Latte.getApplicationContext(),profileJson.toJSONString(), Toast.LENGTH_SHORT).show();*/
        //已经注册并登录成功了
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }
}
