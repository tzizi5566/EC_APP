package com.kop.latte.ui.recycler;

import android.support.annotation.ColorInt;
import com.choices.divider.DividerItemDecoration;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/7 11:48
 */
public class BaseDecoration extends DividerItemDecoration {

  private BaseDecoration(@ColorInt int color, int size) {
    setDividerLookup(new DividerLookupImpl(color, size));
  }

  public static BaseDecoration create(@ColorInt int color, int size) {
    return new BaseDecoration(color, size);
  }
}
