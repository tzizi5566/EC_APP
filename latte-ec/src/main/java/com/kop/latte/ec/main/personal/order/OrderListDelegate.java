package com.kop.latte.ec.main.personal.order;

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
import com.kop.latte.ec.main.personal.PersonalDelegate;
import com.kop.latte.net.rx.RxRestClient;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/8 16:13
 */
public class OrderListDelegate extends LatteDelegate {

  @BindView(R2.id.rv_order_list) RecyclerView mRvOrderList;

  private String mType;

  public static OrderListDelegate newInstance(String orderType) {
    Bundle bundle = new Bundle();
    bundle.putString(PersonalDelegate.ORDER_TYPE, orderType);
    final OrderListDelegate orderListDelegate = new OrderListDelegate();
    orderListDelegate.setArguments(bundle);
    return orderListDelegate;
  }

  @Override public Object setLayout() {
    return R.layout.delegate_order_list;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Bundle bundle = getArguments();
    mType = bundle.getString(PersonalDelegate.ORDER_TYPE);
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    RxRestClient.builder()
        .loader(getContext())
        .url("order_list.json")
        .params("type", mType)
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRvOrderList.setLayoutManager(manager);
            final List<MultipleItemEntity> date =
                new OrderListDataConverter().setJsonData(s).convert();
            final OrderListAdapter adapter = new OrderListAdapter(date);
            mRvOrderList.setAdapter(adapter);
          }

          @Override public void onError(Throwable e) {
            LatteLoader.stopLoading();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {
            LatteLoader.stopLoading();
          }
        });
  }
}
