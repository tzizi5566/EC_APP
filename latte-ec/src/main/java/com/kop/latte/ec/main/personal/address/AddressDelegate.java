package com.kop.latte.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
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
 * 创建日期: 2017/11/11 16:02
 */
public class AddressDelegate extends LatteDelegate {

  @BindView(R2.id.icon_address_add) IconTextView mIconAddressAdd;
  @BindView(R2.id.rv_address) RecyclerView mRvAddress;

  @Override public Object setLayout() {
    return R.layout.delegate_address;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    RxRestClient.builder()
        .loader(getContext())
        .url("address.json")
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRvAddress.setLayoutManager(manager);
            final List<MultipleItemEntity> data =
                new AddressDataConverter().setJsonData(s).convert();
            final AddressAdapter adapter = new AddressAdapter(data);
            mRvAddress.setAdapter(adapter);
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
