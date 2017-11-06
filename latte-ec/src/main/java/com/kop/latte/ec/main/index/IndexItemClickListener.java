package com.kop.latte.ec.main.index;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.detail.GoodsDetailDelegate;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/7 19:55
 */
public class IndexItemClickListener extends SimpleClickListener {

  private final LatteDelegate DELEGATE;

  private IndexItemClickListener(LatteDelegate delegate) {
    this.DELEGATE = delegate;
  }

  public static SimpleClickListener create(LatteDelegate delegate) {
    return new IndexItemClickListener(delegate);
  }

  @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    final GoodsDetailDelegate goodsDetailDelegate = GoodsDetailDelegate.create();
    DELEGATE.getSupportDelegate().start(goodsDetailDelegate);
  }

  @Override public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

  }
}
