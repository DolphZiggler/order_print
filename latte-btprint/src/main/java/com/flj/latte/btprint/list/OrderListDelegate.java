package com.flj.latte.btprint.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flj.latte.btprint.R;
import com.flj.latte.btprint.utils.Constants;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.ui.recycler.BaseDecoration;
import com.flj.latte.ui.refresh.RefreshHandler;


public class OrderListDelegate extends LatteDelegate {

    private String order_tag = null;
    private RecyclerView mRecyclerView = null;
    private SwipeRefreshLayout mRefreshLayout = null;
    private RefreshHandler mRefreshHandler = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order_tag = getArguments().getString("order_tag");
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        String fresh = args.getString("fresh");
        if (!TextUtils.isEmpty(fresh) && fresh.equals("fresh"))
            mRefreshHandler.onRefresh();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRecyclerView = $(R.id.rv_order_list);
        mRefreshLayout = $(R.id.srl_order_list);

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new OrderDataConverter());
        mRefreshHandler.setClickListener(AdapterItemClickListener.create(this, order_tag));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_orange_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final Context context = getContext();
        final LinearLayoutManager manager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(manager);
        if (context != null) {
            mRecyclerView.addItemDecoration
                    (BaseDecoration.create(ContextCompat.getColor(context, R.color.app_background), 5));
        }
//        mRecyclerView.addOnItemTouchListener(OrderItemClickListener.create(this, order_tag));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.with(getContext());
        load();
    }

    private void load() {
        if (order_tag != null) {
            mRefreshHandler.firstPage(Constants.ORDER_LIST, order_tag);
        }
    }
}
