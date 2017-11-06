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

  private long mExitTime = 0;
  private static final int EXIT_TIME = 2000;

  //@Override public void onResume() {
  //  super.onResume();
  //  View view = getView();
  //  if (view != null) {
  //    view.setFocusableInTouchMode(true);
  //    view.requestFocus();
  //    view.setOnKeyListener(this);
  //  }
  //}

  //@Override public boolean onKey(View v, int keyCode, KeyEvent event) {
  //  if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
  //    if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME) {
  //      Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT)
  //          .show();
  //      mExitTime = System.currentTimeMillis();
  //    } else {
  //      _mActivity.finish();
  //      if (mExitTime != 0) {
  //        mExitTime = 0;
  //      }
  //    }
  //
  //    return true;
  //  }
  //
  //  return false;
  //}

  @Override public boolean onBackPressedSupport() {
    if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME) {
      Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT)
          .show();
      mExitTime = System.currentTimeMillis();
    } else {
      _mActivity.finish();
      if (mExitTime != 0) {
        mExitTime = 0;
      }
    }
    return true;
  }
}
