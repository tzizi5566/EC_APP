package com.kop.latte.ui.recycler;

import android.view.View;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 19:27
 */
public class MultipleViewHolder extends BaseViewHolder {

  private MultipleViewHolder(View view) {
    super(view);
  }

  public static MultipleViewHolder create(View view) {
    return new MultipleViewHolder(view);
  }
}
