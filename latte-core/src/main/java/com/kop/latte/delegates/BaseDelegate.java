package com.kop.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.kop.latte.activities.ProxyActivity;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 20:54
 */
public abstract class BaseDelegate extends SwipeBackFragment {

  private static final String TAG = "BaseDelegate";

  private Unbinder mUnbinder = null;

  public abstract Object setLayout();

  public abstract void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView);

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View rootView;
    if (setLayout() instanceof Integer) {
      rootView = inflater.inflate((int) setLayout(), container, false);
    } else if (setLayout() instanceof View) {
      rootView = (View) setLayout();
    } else {
      throw new ClassCastException("type of setLayout() must be int or View!");
    }

    mUnbinder = ButterKnife.bind(this, rootView);
    onBindView(savedInstanceState, rootView);

    return rootView;
  }

  public final ProxyActivity getProxyActivity() {
    return (ProxyActivity) _mActivity;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (mUnbinder != null) {
      mUnbinder.unbind();
    }
  }
}
