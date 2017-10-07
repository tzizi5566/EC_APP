package com.kop.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 20:29
 */
public enum EcIcons implements Icon {
  icon_scan('\ue602'),
  icon_ali_pay('\ue606');

  private char mChar;

  EcIcons(char character) {
    this.mChar = character;
  }

  @Override public String key() {
    return this.name().replace('_', '-');
  }

  @Override public char character() {
    return mChar;
  }
}
