package com.kop.latte.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/7 15:09
 */
@AutoValue
public abstract class RgbValue {

  public abstract int red();

  public abstract int green();

  public abstract int blue();

  public static RgbValue create(int red, int green, int blue) {
    return new AutoValue_RgbValue(red, green, blue);
  }
}
