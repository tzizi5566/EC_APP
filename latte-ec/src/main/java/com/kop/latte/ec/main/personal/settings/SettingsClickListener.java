package com.kop.latte.ec.main.personal.settings;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.ec.main.personal.list.ListBean;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/12 15:50
 */
public class SettingsClickListener extends SimpleClickListener {

  private final LatteDelegate DELEGATE;

  SettingsClickListener(LatteDelegate delegate) {
    this.DELEGATE = delegate;
  }

  @Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
    int id = bean.getId();
    switch (id) {
      case 1:

        break;

      case 2:
        DELEGATE.getSupportDelegate().start(bean.getDelegate());
        break;

      default:

        break;
    }
  }

  @Override public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

  }

  @Override public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

  }
}
