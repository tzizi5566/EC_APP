package com.kop.latte.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 20:22
 */
public class HolderCreator implements CBViewHolderCreator<ImageHolder>{

  @Override public ImageHolder createHolder() {
    return new ImageHolder();
  }
}
