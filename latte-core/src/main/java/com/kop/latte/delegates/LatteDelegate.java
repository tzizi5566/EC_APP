package com.kop.latte.delegates;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 21:11
 */
public abstract class LatteDelegate extends PermissionCheckerDelegate {

  public <T extends LatteDelegate> T getParentDelegate() {
    return (T) getParentFragment();
  }
}
