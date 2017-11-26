package com.kop.latte.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kop.latte.app.Latte;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.ui.recycler.DataConverter;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 16:47
 */
public class RefreshHandler
    implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

  private Context mContext;
  private final SwipeRefreshLayout REFRESH_LAYOUT;
  private final PagingBean BEAN;
  private final RecyclerView RECYCLERVIEW;
  private MultipleRecyclerAdapter mAdapter;
  private final DataConverter CONVERTER;

  private RefreshHandler(Context context, SwipeRefreshLayout refreshLayout,
      RecyclerView recyclerView,
      DataConverter converter, PagingBean bean) {
    this.mContext = context;
    this.REFRESH_LAYOUT = refreshLayout;
    this.RECYCLERVIEW = recyclerView;
    this.CONVERTER = converter;
    this.BEAN = bean;
    REFRESH_LAYOUT.setOnRefreshListener(this);
  }

  public static RefreshHandler create(Context context, SwipeRefreshLayout refreshLayout,
      RecyclerView recyclerView, DataConverter converter) {
    return new RefreshHandler(context, refreshLayout, recyclerView, converter, new PagingBean());
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
    RxRestClient.builder()
        .loader(mContext)
        .url(url)
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final JSONObject jsonObject = JSON.parseObject(s);
            BEAN.setTotal(jsonObject.getInteger("total"))
                .setPageSize(jsonObject.getInteger("page_size"));
            mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(s));
            mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
            RECYCLERVIEW.setAdapter(mAdapter);
            BEAN.addIndex();
          }

          @Override public void onError(Throwable e) {
            LatteLoader.stopLoading();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {
            LatteLoader.stopLoading();
          }
        });
  }

  private void paging() {
    final int pageSize = BEAN.getPageSize();
    final int currentCount = BEAN.getCurrentCount();
    final int total = BEAN.getTotal();
    final int index = BEAN.getPageIndex();

    if (mAdapter.getData().size() < pageSize || currentCount >= total) {
      mAdapter.loadMoreEnd(true);
    } else {
      RxRestClient.builder()
          .url("index_2_data.json")
          .build()
          .get()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<String>() {
            @Override public void onSubscribe(Disposable d) {

            }

            @Override public void onNext(String s) {
              ArrayList<MultipleItemEntity> convert = CONVERTER.setJsonData(s).convert();
              mAdapter.addData(CONVERTER.setJsonData(s).convert());
              //累加数量
              BEAN.setCurrentCount(mAdapter.getData().size());
              mAdapter.loadMoreComplete();
              BEAN.addIndex();
            }

            @Override public void onError(Throwable e) {
              LatteLoader.stopLoading();
              mAdapter.loadMoreFail();
              Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override public void onComplete() {
              LatteLoader.stopLoading();
            }
          });
    }
  }

  @Override public void onRefresh() {
    refresh();
  }

  @Override public void onLoadMoreRequested() {
    paging();
  }
}
