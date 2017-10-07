package com.kop.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/7 20:06
 */
public class GoodsDetailDelegate extends LatteDelegate {

  public static GoodsDetailDelegate create() {
    return new GoodsDetailDelegate();
  }

  @Override public Object setLayout() {
    return R.layout.delegate_goods_detail;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public FragmentAnimator onCreateFragmentAnimator() {
    return new DefaultHorizontalAnimator();
  }
}
