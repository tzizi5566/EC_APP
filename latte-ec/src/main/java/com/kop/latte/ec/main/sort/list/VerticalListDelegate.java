package com.kop.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ec.main.sort.SortDelegate;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/8 17:38
 */
public class VerticalListDelegate extends LatteDelegate {

  @BindView(R2.id.rv_vertical_menu_list) RecyclerView mRvVerticalMenuList;

  @Override public Object setLayout() {
    return R.layout.delegate_vertical_list;
  }

  private void initRecyclerView() {
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    mRvVerticalMenuList.setLayoutManager(linearLayoutManager);
    //屏蔽动画效果
    mRvVerticalMenuList.setItemAnimator(null);
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    initRecyclerView();
  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    RxRestClient.builder()
        .url("sort_list_data.json")
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final List<MultipleItemEntity> data =
                new VerticalListDataConverter().setJsonData(s).convert();
            final SortDelegate sortDelegate = getParentDelegate();
            final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, sortDelegate);
            mRvVerticalMenuList.setAdapter(adapter);
          }

          @Override public void onError(Throwable e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {

          }
        });
  }
}
