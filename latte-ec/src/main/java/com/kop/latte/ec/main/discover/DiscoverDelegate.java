package com.kop.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.delegates.web.WebDelegateImpl;
import com.kop.latte.ec.R;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 13:02
 */
public class DiscoverDelegate extends BottomItemDelegate {

  @Override public Object setLayout() {
    return R.layout.delegate_discover;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    final WebDelegateImpl delegate = WebDelegateImpl.create("index.html");
    delegate.setTopDelegate(this.getParentDelegate());
    loadRootFragment(R.id.web_discovery_container, delegate);
  }

  @Override public FragmentAnimator onCreateFragmentAnimator() {
    return new DefaultHorizontalAnimator();
  }
}
