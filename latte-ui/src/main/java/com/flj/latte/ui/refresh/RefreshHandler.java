package com.flj.latte.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flj.latte.app.AccountManager;
import com.flj.latte.app.Latte;
import com.flj.latte.net.RestClient;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.util.log.LatteLogger;

/**
 * Created by gg
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;
    private String pageUrl = null;
    private String type = null;
    private Context activityContext = null;
    private int pageSize = 10;
    private BaseQuickAdapter.OnItemClickListener clickListener;

    public void setClickListener(BaseQuickAdapter.OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(() -> {
            resetBean();
            CONVERTER.clearData();
            firstPage(pageUrl, type);
            REFRESH_LAYOUT.setRefreshing(false);
        }, 0);
    }

    private void resetBean() {
        BEAN.setCurrentCount(0).setDelayed(0).setPageIndex(1).setTotal(0);
    }

    public void firstPage(String... args) {
        pageUrl = args[0];
        type = args[1];
        LatteLogger.d("IUDHAS", args[0]);
        BEAN.setDelayed(1000);
        Latte.getHandler().postDelayed((
                        () -> RestClient.builder()
                                .url(pageUrl)
                                .params("type", type)
                                .params("userid", AccountManager.getCurrentCustomerId())
                                .params("page_size", pageSize)
                                .params("page", BEAN.getPageIndex())
                                .loader(activityContext)
                                .success(response -> {
                                    final JSONObject object = JSON.parseObject(response);
                                    BEAN.setTotal(object.getInteger("total"))
                                            .setPageSize(object.getInteger("page_size"));
                                    //设置Adapter
                                    mAdapter = MultipleRecyclerAdapter.create(null);
                                    mAdapter.openLoadAnimation();
                                    mAdapter.setOnItemClickListener(clickListener);
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                                    RECYCLERVIEW.setAdapter(mAdapter);
                                    BEAN.addIndex();
                                })
                                .error((code, msg) -> Toast.makeText(Latte.getApplicationContext(), "请求错误：" + code + " " + msg, Toast.LENGTH_SHORT).show())
                                .failure(() -> Toast.makeText(Latte.getApplicationContext(), "网络故障，请求失败", Toast.LENGTH_SHORT).show())
                                .build()
                                .get())
                , 0);
    }


    public void with(Context c) {
        this.activityContext = c;
    }


    private void paging() {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();

        if (mAdapter.getData().size() < pageSize || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
        } else {
            Latte.getHandler().postDelayed(
                    () -> RestClient.builder()
                            .url(pageUrl)
                            .params("type", type)
                            .params("userid", AccountManager.getCurrentCustomerId())
                            .params("page_size", pageSize)
                            .params("page", BEAN.getPageIndex())
                            .loader(activityContext)
                            .success(response -> {
                                LatteLogger.json("paging", response);
                                CONVERTER.clearData();

                                mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                BEAN.setCurrentCount(mAdapter.getData().size());
                                BEAN.addIndex();

                                mAdapter.loadMoreComplete();
                            })
                            .error(((code, msg) -> Toast.makeText(Latte.getApplicationContext(), "请求错误：" + code + " " + msg, Toast.LENGTH_SHORT).show()))
                            .failure(() -> Toast.makeText(activityContext, "网络故障，请求失败", Toast.LENGTH_SHORT).show())
                            .build()
                            .get()
                    , 500);
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }


    @Override
    public void onLoadMoreRequested() {
        paging();
    }
}
