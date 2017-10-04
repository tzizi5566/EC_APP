package com.kop.latte.app;

import com.kop.latte.util.storage.LattePreference;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/4 17:21
 */
public class AccountManager {

  private enum SignTag {
    SIGN_TAG
  }

  //保存用户登录状态，登录后调用
  public static void setSignState(boolean state) {
    LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
  }

  private static boolean isSignIn() {
    return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
  }

  public static void checkAccount(IUserChecker userChecker) {
    if (isSignIn()) {
      userChecker.onSignIn();
    } else {
      userChecker.onNotSignIn();
    }
  }
}
