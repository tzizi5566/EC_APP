package com.kop.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.kop.latte.app.AccountManager;
import com.kop.latte.app.IUserChecker;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.R2;
import com.kop.latte.ui.launcher.ILauncherListener;
import com.kop.latte.ui.launcher.OnLauncherFinishTag;
import com.kop.latte.ui.launcher.ScrollLauncherTag;
import com.kop.latte.util.storage.LattePreference;
import com.kop.latte.util.timer.BaseTimerTask;
import com.kop.latte.util.timer.ITimerListener;
import java.text.MessageFormat;
import java.util.Timer;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/10 20:36
 */
public class LauncherDelegate extends LatteDelegate implements ITimerListener {

  @BindView(R2.id.tv_launcher_timer)
  AppCompatTextView mTvTimer;

  private Timer mTimer = null;
  private int mCount = 5;
  private ILauncherListener mILauncherListener;

  @OnClick(R2.id.tv_launcher_timer)
  void OnClickTimerView() {
    if (mTimer != null) {
      mTimer.cancel();
      mTimer = null;
      checkIsShowScroll();
    }
  }

  private void initTimer() {
    mTimer = new Timer();
    final BaseTimerTask timerTask = new BaseTimerTask(this);
    mTimer.schedule(timerTask, 0, 1000);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof ILauncherListener) {
      mILauncherListener = (ILauncherListener) activity;
    }
  }

  @Override public Object setLayout() {
    return R.layout.delegate_launcher;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    initTimer();
  }

  private void checkIsShowScroll() {
    if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
      getSupportDelegate().startWithPop(new LauncherScrollDelegater());
    } else {
      AccountManager.checkAccount(new IUserChecker() {
        @Override public void onSignIn() {
          if (mILauncherListener != null) {
            mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
          }
        }

        @Override public void onNotSignIn() {
          if (mILauncherListener != null) {
            mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
          }
        }
      });
    }
    getSupportDelegate().pop();
  }

  @Override public void onTimer() {
    getProxyActivity().runOnUiThread(new Runnable() {
      @Override public void run() {
        if (mTvTimer != null) {
          mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
          mCount--;
          if (mCount < 0) {
            if (mTimer != null) {
              mTimer.cancel();
              mTimer = null;
              checkIsShowScroll();
            }
          }
        }
      }
    });
  }
}
