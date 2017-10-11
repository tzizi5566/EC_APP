package com.kop.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.main.sort.content.ContentDelegate;
import com.kop.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 17:40
 */
public class SortDelegate extends BottomItemDelegate {

  @Override public Object setLayout() {
    return R.layout.delegate_sort;
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {

  }

  @Override public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    if (savedInstanceState == null) {
      final VerticalListDelegate verticalListDelegate = new VerticalListDelegate();
      loadRootFragment(R.id.vertical_list_container, verticalListDelegate);
      loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
    }
  }
}
