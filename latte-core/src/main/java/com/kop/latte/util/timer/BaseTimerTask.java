package com.kop.latte.util.timer;

import java.util.TimerTask;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/10 20:37
 */
public class BaseTimerTask extends TimerTask {

  private ITimerListener mITimerListener = null;

  public BaseTimerTask(ITimerListener timerListener) {
    mITimerListener = timerListener;
  }

  @Override public void run() {
    if (mITimerListener != null) {
      mITimerListener.onTimer();
    }
  }
}
