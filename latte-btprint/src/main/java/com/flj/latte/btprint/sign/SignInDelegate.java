package com.flj.latte.btprint.sign;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.flj.latte.app.AccountManager;
import com.flj.latte.btprint.R;
import com.flj.latte.btprint.utils.Constants;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.util.storage.LattePreference;
import com.z2wenfa.spinneredittext.SpinnerEditText;

import java.util.Arrays;

import static com.flj.latte.app.Latte.getApplicationContext;


/**
 * Created by gg on 2017/4/22
 */

public class SignInDelegate extends LatteDelegate implements View.OnClickListener {

    private SpinnerEditText<String> mAccount = null;
    private SpinnerEditText mPassword = null;
    private ISignListener mISignListener = null;
    private String[] historyAccount = null;
    private String[] historyPassword = null;

    @Override

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    private void onClickSignIn() {
        if (checkForm()) {
            RestClient.builder()
                    .url(Constants.LOGIN)
                    .loader(getContext())
                    .params("Opnum", mAccount.getText().toString().trim())
                    .params("Userpassword", mPassword.getText().toString().trim())
                    .success((response) -> {
                        int code = Integer.valueOf(response);
                        if (code > 0) {
                            AccountManager.add2Customers(mAccount.getText().toString().trim(), mPassword.getText().toString().trim(),response);
                            AccountManager.setCurrentCustomer(mAccount.getText().toString().trim(), mPassword.getText().toString().trim(),response);
                            SignHandler.onSignIn(response, mISignListener);
                        } else {
                            Toast.makeText(_mActivity, "用户名密码错误", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .error(((code, msg) ->
                            Toast.makeText(_mActivity, "登录错误：" + code, Toast.LENGTH_SHORT).show())
                    )
                    .failure(() -> Toast.makeText(_mActivity, "网络故障，登录失败", Toast.LENGTH_SHORT).show())
                    .build()
                    .post();
        }
    }

    private boolean checkForm() {
        final String account = mAccount.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;
        if (account.isEmpty()) {
            mAccount.setError("请填写正确的用户名");
            isPass = false;
        } else {
            mAccount.setError(null);
        }

        if (password.isEmpty()) {
            mPassword.setError("请填写正确的密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intiHistory();
    }

    private void intiHistory() {
        historyAccount = AccountManager.getCustomerNames();
        historyPassword = AccountManager.getCustomerPwds();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mAccount = $(R.id.edit_sign_in_account);
        mPassword = $(R.id.edit_sign_in_password);
        $(R.id.btn_sign_in).setOnClickListener(this);
        initAcountEdit();
    }

    private void initAcountEdit() {
        mAccount.setList(Arrays.asList(historyAccount));
        mAccount.setOnItemClickListener((String s, SpinnerEditText<String> spinnerEditText, View view, int i, long l, String s2) -> {
            mPassword.setText(historyPassword[i]);
        });
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_sign_in) {
            hideSoftInputMethodWindow();
            onClickSignIn();
        }
    }

    private void hideSoftInputMethodWindow() {
        InputMethodManager im = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getProxyActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
