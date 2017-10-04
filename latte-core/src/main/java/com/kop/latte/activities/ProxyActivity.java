package com.kop.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import com.kop.latte.R;
import com.kop.latte.delegates.LatteDelegate;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 20:51
 */
public abstract class ProxyActivity extends SupportActivity {

  private static final String TAG = "ProxyActivity";

  public abstract LatteDelegate setRootDelegate();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initContainer(savedInstanceState);
  }

  private void initContainer(@Nullable Bundle savedInstanceState) {
    final ContentFrameLayout frameLayout = new ContentFrameLayout(this);
    frameLayout.setId(R.id.delegate_container);
    setContentView(frameLayout);
    if (savedInstanceState == null) {
      loadRootFragment(R.id.delegate_container, setRootDelegate());
    }
  }

  //取消Fragment转场动画
  @Override public FragmentAnimator onCreateFragmentAnimator() {
    return new DefaultNoAnimator();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    System.gc();
    System.runFinalization();
  }
}
