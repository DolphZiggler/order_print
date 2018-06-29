package com.flj.latte.btprint.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.flj.latte.app.AccountManager;
import com.flj.latte.app.IUserChecker;
import com.flj.latte.btprint.R;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ui.launcher.ILauncherListener;
import com.flj.latte.ui.launcher.OnLauncherFinishTag;


public class LauncherDelegate extends LatteDelegate {
    private ILauncherListener mILauncherListener = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        checkAccount();
    }

    private void checkAccount() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                if (mILauncherListener!=null){
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSignIn() {
                if (mILauncherListener!=null){
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }
}
