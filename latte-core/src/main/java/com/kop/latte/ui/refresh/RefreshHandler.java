package com.kop.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kop.latte.app.Latte;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.ui.recycler.DataConverter;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.util.log.LatteLogger;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 16:47
 */
public class RefreshHandler
    implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

  private final SwipeRefreshLayout REFRESH_LAYOUT;
  private final PagingBean BEAN;
  private final RecyclerView RECYCLERVIEW;
  private MultipleRecyclerAdapter mAdapter;
  private final DataConverter CONVERTER;

  private RefreshHandler(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView,
      DataConverter converter, PagingBean bean) {
    this.REFRESH_LAYOUT = refreshLayout;
    this.RECYCLERVIEW = recyclerView;
    this.CONVERTER = converter;
    this.BEAN = bean;
    REFRESH_LAYOUT.setOnRefreshListener(this);
  }

  public static RefreshHandler create(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView,
      DataConverter converter) {
    return new RefreshHandler(refreshLayout, recyclerView, converter, new PagingBean());
  }

  private void refresh() {
    REFRESH_LAYOUT.setRefreshing(true);
    Latte.getHandler().postDelayed(new Runnable() {
      @Override public void run() {
        REFRESH_LAYOUT.setRefreshing(false);
      }
    }, 2000);
  }

  public void firstPage(String url) {
    BEAN.setDelayed(1000);
    RestClient.builder()
        .url(url)
        .success(new ISuccess() {
          @Override public void onSuccess(String response) {
            final JSONObject jsonObject = JSON.parseObject(response);
            BEAN.setTotal(jsonObject.getInteger("total"))
                .setPageSize(jsonObject.getInteger("page_size"));
            mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
            mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
            RECYCLERVIEW.setAdapter(mAdapter);
            BEAN.addIndex();
          }
        })
        .build()
        .get();
  }

  private void paging() {
    final int pageSize = BEAN.getPageSize();
    final int currentCount = BEAN.getCurrentCount();
    final int total = BEAN.getTotal();
    final int index = BEAN.getPageIndex();

    if (mAdapter.getData().size() < pageSize || currentCount >= total) {
      mAdapter.loadMoreEnd(true);
    } else {
      RestClient.builder()
          .url("index_2_data.json")
          .success(new ISuccess() {
            @Override public void onSuccess(String response) {
              ArrayList<MultipleItemEntity> convert = CONVERTER.setJsonData(response).convert();
              LatteLogger.d(convert.size());
              mAdapter.addData(CONVERTER.setJsonData(response).convert());
              //累加数量
              BEAN.setCurrentCount(mAdapter.getData().size());
              mAdapter.loadMoreComplete();
              BEAN.addIndex();
            }
          })
          .build()
          .get();
    }
  }

  @Override public void onRefresh() {
    refresh();
  }

  @Override public void onLoadMoreRequested() {
    paging();
  }
}
