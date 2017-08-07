package com.kop.latte.ec.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 20:28
 */
public class FontEcModule implements IconFontDescriptor {

  @Override public String ttfFileName() {
    return "iconfont.ttf";
  }

  @Override public Icon[] characters() {
    return EcIcons.values();
  }
}
