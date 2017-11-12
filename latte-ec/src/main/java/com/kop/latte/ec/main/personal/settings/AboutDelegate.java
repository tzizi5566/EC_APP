package com.kop.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.net.rx.RxRestClient;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/12 15:53
 */
public class AboutDelegate extends LatteDelegate {

  @BindView(R2.id.tv_info) AppCompatTextView mTvInfo;

  @Override public Object setLayout() {
    return R.layout.delegate_about;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    RxRestClient.builder()
        .url("about.json")
        .build()
        .get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(String s) {
            final String info = JSON.parseObject(s).getString("data");
            mTvInfo.setText(info);
          }

          @Override public void onError(Throwable e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }

          @Override public void onComplete() {

          }
        });
  }
}
