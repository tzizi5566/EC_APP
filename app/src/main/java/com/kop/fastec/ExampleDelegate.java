package com.kop.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.net.RestClient;
import com.kop.latte.net.callback.IError;
import com.kop.latte.net.callback.IFailure;
import com.kop.latte.net.callback.ISuccess;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 21:20
 */
public class ExampleDelegate extends LatteDelegate {

  private static final String TAG = "ExampleDelegate";

  @Override public Object setLayout() {
    return R.layout.delegate_example;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    testRestClient();
  }

  private void testRestClient() {
    RestClient.builder()
        .url("https://www.baidu.com/")
        .loader(getContext())
        .success(new ISuccess() {
          @Override public void onSuccess(String response) {

          }
        })
        .failure(new IFailure() {
          @Override public void onFailure() {

          }
        })
        .error(new IError() {
          @Override public void onError(int code, String msg) {

          }
        })
        .build()
        .get();
  }
}
