package com.kop.latte.ec.main.personal.order;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/13 13:40
 */
public class OrderListClickListener extends SimpleClickListener {

  private final LatteDelegate DELEGATE;

  OrderListClickListener(LatteDelegate delegate) {
    this.DELEGATE = delegate;
  }

  @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
    DELEGATE.getSupportDelegate().start(OrderCommentDelegate.newInstance((String) entity.getField(
        MultipleFields.IMAGE_URL)));
  }

  @Override public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

  }
}
