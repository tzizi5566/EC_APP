package com.kop.latte.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/7 11:50
 */
public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {

  private final int COLOR;
  private final int SIZE;

  public DividerLookupImpl(int color, int size) {
    this.COLOR = color;
    this.SIZE = size;
  }

  @Override public Divider getVerticalDivider(int position) {
    return new Divider.Builder().size(SIZE).color(COLOR).build();
  }

  @Override public Divider getHorizontalDivider(int position) {
    return new Divider.Builder().size(SIZE).color(COLOR).build();
  }
}
