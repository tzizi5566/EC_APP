package com.kop.latte.delegates.bottom;

import android.widget.Toast;
import com.kop.latte.R;
import com.kop.latte.delegates.LatteDelegate;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 16:07
 */
public abstract class BottomItemDelegate extends LatteDelegate {

  // 再点一次退出程序时间设置
  private static final long WAIT_TIME = 2000L;
  private long TOUCH_TIME = 0;

  /**
   * 处理回退事件
   */
  @Override
  public boolean onBackPressedSupport() {
    if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
      _mActivity.finish();
    } else {
      TOUCH_TIME = System.currentTimeMillis();
      Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
    }
    return true;
  }
}
