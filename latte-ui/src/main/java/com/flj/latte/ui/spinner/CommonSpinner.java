package com.flj.latte.ui.spinner;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.flj.latte.ui.R;

public class CommonSpinner extends AppCompatTextView {
    private Context mContext;
    private ArrayAdapter adapter;
    private ListView popContentView;
    private AdapterView.OnItemClickListener onItemClickListener;
    private PopupWindow mDropView;

    public CommonSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        LinearLayout container = (LinearLayout) inflater.inflate(R.layout.spinner_content, null);
        popContentView = container.findViewById(R.id.spinner_content);
        mDropView = new PopupWindow(container, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDropView.setBackgroundDrawable(new BitmapDrawable());
        mDropView.setFocusable(true);
        mDropView.setOutsideTouchable(true);
        mDropView.setOutsideTouchable(true);
        mDropView.setTouchable(true);
        container.setOnClickListener((v) -> {
            dismissPop();
        });
        this.setOnClickListener((v) -> {
            if (mDropView.isShowing()) {
                dismissPop();
            } else {
                showPop();
            }
        });
        mDropView.update();
    }

    public CommonSpinner setHint(String hint) {
        this.setText(hint);
        return this;
    }

    public void setAdapter(ArrayAdapter adapter) {
        if (adapter != null) {
            this.adapter = adapter;
            popContentView.setAdapter(this.adapter);
        }

    }

    public void setOnItemSelectedListener(AdapterView.OnItemClickListener listener) {
        if (listener != null) {
            this.onItemClickListener = listener;
            popContentView.setOnItemClickListener(listener);
        }

    }

    public CommonSpinner dismissPop() {
        if (mDropView.isShowing()) {
            mDropView.dismiss();
        }
        return this;
    }

    public void showPop() {
        mDropView.showAsDropDown(this);
    }

}