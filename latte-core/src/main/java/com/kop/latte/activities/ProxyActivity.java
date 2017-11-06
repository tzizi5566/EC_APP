package com.kop.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import com.kop.latte.R;
import com.kop.latte.delegates.LatteDelegate;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 20:51
 */
public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity {

  private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

  public abstract LatteDelegate setRootDelegate();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DELEGATE.onCreate(savedInstanceState);
    initContainer(savedInstanceState);
  }

  private void initContainer(@Nullable Bundle savedInstanceState) {
    final ContentFrameLayout frameLayout = new ContentFrameLayout(this);
    frameLayout.setId(R.id.delegate_container);
    setContentView(frameLayout);
    if (savedInstanceState == null) {
      DELEGATE.loadRootFragment(R.id.delegate_container, setRootDelegate());
    }
  }

  @Override protected void onDestroy() {
    DELEGATE.onDestroy();
    super.onDestroy();
    System.gc();
    System.runFinalization();
  }

  @Override
  public SupportActivityDelegate getSupportDelegate() {
    return DELEGATE;
  }

  @Override
  public ExtraTransaction extraTransaction() {
    return DELEGATE.extraTransaction();
  }

  @Override
  public FragmentAnimator getFragmentAnimator() {
    return DELEGATE.getFragmentAnimator();
  }

  @Override
  public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
    DELEGATE.setFragmentAnimator(fragmentAnimator);
  }

  @Override
  public FragmentAnimator onCreateFragmentAnimator() {
    return DELEGATE.onCreateFragmentAnimator();
  }

  @Override
  public void onBackPressedSupport() {
    DELEGATE.onBackPressedSupport();
  }

  @Override
  public void onBackPressed() {
    DELEGATE.onBackPressed();
  }
}
