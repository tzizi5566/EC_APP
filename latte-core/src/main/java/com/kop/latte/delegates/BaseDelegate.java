package com.kop.latte.delegates;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.kop.latte.activities.ProxyActivity;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 20:54
 */
public abstract class BaseDelegate extends Fragment implements ISupportFragment {

  final SupportFragmentDelegate DELEGATE = new SupportFragmentDelegate(this);
  protected FragmentActivity _mActivity = null;

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

  @Override
  public SupportFragmentDelegate getSupportDelegate() {
    return DELEGATE;
  }

  @Override
  public ExtraTransaction extraTransaction() {
    return DELEGATE.extraTransaction();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    DELEGATE.onAttach(activity);
    _mActivity = DELEGATE.getActivity();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DELEGATE.onCreate(savedInstanceState);
  }

  @Override
  public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    return DELEGATE.onCreateAnimation(transit, enter, nextAnim);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    DELEGATE.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    DELEGATE.onSaveInstanceState(outState);
  }

  @Override
  public void onResume() {
    super.onResume();
    DELEGATE.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    DELEGATE.onPause();
  }

  @Override
  public void onDestroyView() {
    DELEGATE.onDestroyView();
    super.onDestroyView();
    if (mUnbinder != null) {
      mUnbinder.unbind();
    }
  }

  @Override
  public void onDestroy() {
    DELEGATE.onDestroy();
    super.onDestroy();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    DELEGATE.onHiddenChanged(hidden);
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    DELEGATE.setUserVisibleHint(isVisibleToUser);
  }

  @Override
  public void enqueueAction(Runnable runnable) {
    DELEGATE.enqueueAction(runnable);
  }

  @Override
  public void onEnterAnimationEnd(Bundle savedInstanceState) {
    DELEGATE.onEnterAnimationEnd(savedInstanceState);
  }

  @Override
  public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    DELEGATE.onLazyInitView(savedInstanceState);
  }

  @Override
  public void onSupportVisible() {
    DELEGATE.onSupportVisible();
  }

  @Override
  public void onSupportInvisible() {
    DELEGATE.onSupportInvisible();
  }

  @Override final public boolean isSupportVisible() {
    return DELEGATE.isSupportVisible();
  }

  @Override
  public FragmentAnimator onCreateFragmentAnimator() {
    return DELEGATE.onCreateFragmentAnimator();
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
  public boolean onBackPressedSupport() {
    return DELEGATE.onBackPressedSupport();
  }

  @Override
  public void setFragmentResult(int resultCode, Bundle bundle) {
    DELEGATE.setFragmentResult(resultCode, bundle);
  }

  @Override
  public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
    DELEGATE.onFragmentResult(requestCode, resultCode, data);
  }

  @Override
  public void onNewBundle(Bundle args) {
    DELEGATE.onNewBundle(args);
  }

  @Override
  public void putNewBundle(Bundle newBundle) {
    DELEGATE.putNewBundle(newBundle);
  }
}
