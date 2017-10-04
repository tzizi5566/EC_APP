package com.kop.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.kop.latte.app.AccountManager;
import com.kop.latte.app.IUserChecker;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ui.launcher.ILauncherListener;
import com.kop.latte.ui.launcher.LauncherHolderCreator;
import com.kop.latte.ui.launcher.OnLauncherFinishTag;
import com.kop.latte.ui.launcher.ScrollLauncherTag;
import com.kop.latte.util.storage.LattePreference;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/10 21:40
 */
public class LauncherScrollDelegater extends LatteDelegate implements OnItemClickListener {

  private ConvenientBanner<Integer> mConvenientBanner = null;
  private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
  private ILauncherListener mILauncherListener;

  private void initBanner() {
    INTEGERS.add(R.mipmap.launcher_01);
    INTEGERS.add(R.mipmap.launcher_02);
    INTEGERS.add(R.mipmap.launcher_03);
    INTEGERS.add(R.mipmap.launcher_04);
    INTEGERS.add(R.mipmap.launcher_05);

    mConvenientBanner
        .setPages(new LauncherHolderCreator(), INTEGERS)
        .setPageIndicator(new int[] { R.drawable.dot_normal, R.drawable.dot_focus })
        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
        .setOnItemClickListener(this)
        .setCanLoop(false);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ILauncherListener) {
      mILauncherListener = (ILauncherListener) activity;
    }
  }

  @Override public Object setLayout() {
    mConvenientBanner = new ConvenientBanner<>(getContext());
    return mConvenientBanner;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    initBanner();
  }

  @Override public void onItemClick(int position) {
    //如果点击的是最后一个
    if (position == INTEGERS.size() - 1) {
      LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
      //检查用户是否已经登录
      AccountManager.checkAccount(new IUserChecker() {
        @Override
        public void onSignIn() {
          if (mILauncherListener != null) {
            mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
          }
        }

        @Override
        public void onNotSignIn() {
          if (mILauncherListener != null) {
            mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
          }
        }
      });
    }
  }
}
