package com.flj.latte.btprint.index;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.flj.latte.app.AccountManager;
import com.flj.latte.btprint.R;
import com.flj.latte.btprint.list.OrderListDelegate;
import com.flj.latte.btprint.sign.SignInDelegate;
import com.flj.latte.btprint.upload.UploadDelegate;
import com.flj.latte.delegates.LatteDelegate;


/**
 * Created by gg on 2017/4/22
 */

public class IndexDelegate extends LatteDelegate implements View.OnClickListener {
    private AppCompatTextView tv_title = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        tv_title = $(R.id.tv_index_title);
        tv_title.setText(AccountManager.getCurrentCustomerName());

        $(R.id.btn_unsend).setOnClickListener(this);
        $(R.id.btn_sending).setOnClickListener(this);
        $(R.id.btn_done).setOnClickListener(this);
        $(R.id.btn_upload).setOnClickListener(this);
        $(R.id.btn_reset).setOnClickListener(this);
        $(R.id.btn_exchange).setOnClickListener(this);

        $(R.id.btn_unsend).setTag(1);
        $(R.id.btn_sending).setTag(2);
        $(R.id.btn_done).setTag(3);
        $(R.id.btn_upload).setTag(4);
        $(R.id.btn_reset).setTag(5);
        $(R.id.btn_exchange).setTag(6);

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (tag == 4) {
            start(new UploadDelegate(), SINGLETOP);
        } else if (tag == 5) {
            reset();
        } else if (tag == 6) {
            exchange();
        } else {
            jump(tag);
        }
    }

    private void exchange() {
        new AlertDialog.Builder(getContext())
                .setItems(AccountManager.getCustomerNames(), (DialogInterface dialogInterface, int i) -> {
                    AccountManager.setCurrentCustomerID(AccountManager.getCustomerID()[i]);
                    AccountManager.setCurrentCustomerName(AccountManager.getCustomerNames()[i]);
                    tv_title.setText(AccountManager.getCurrentCustomerName());
                }).create().show();
    }

    private void reset() {
        AccountManager.logout();
        getSupportDelegate().startWithPop(new SignInDelegate());
    }


    private void jump(int delegateTag) {
        if (delegateTag == 1 || delegateTag == 2 || delegateTag == 3) {
            OrderListDelegate delegate = new OrderListDelegate();
            Bundle bundle = new Bundle();
            bundle.putString("order_tag", String.valueOf(delegateTag));
            delegate.setArguments(bundle);
            getSupportDelegate().start(delegate, SINGLETASK);
        }
    }
}
