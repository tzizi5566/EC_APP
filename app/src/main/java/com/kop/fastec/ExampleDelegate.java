package com.kop.fastec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.LatteDelegate;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 21:20
 */
public class ExampleDelegate extends LatteDelegate {

  @Override public Object setLayout() {
    return R.layout.delegate_example;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

  }
}
