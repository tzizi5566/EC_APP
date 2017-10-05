package com.kop.latte.delegates.bottom;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 16:22
 */
public final class BottomTabBean {

  private final CharSequence ICON;
  private final CharSequence TITLE;

  public BottomTabBean(CharSequence icon, CharSequence title) {
    this.ICON = icon;
    this.TITLE = title;
  }

  public CharSequence getIcon() {
    return ICON;
  }

  public CharSequence getTitle() {
    return TITLE;
  }
}
