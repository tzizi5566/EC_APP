package com.kop.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.ec.R;
import com.kop.latte.ec.main.sort.content.ContentDelegate;
import com.kop.latte.ec.main.sort.list.VerticalListDelegate;
import me.yokeyword.fragmentation.SupportHelper;

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
    final VerticalListDelegate verticalListDelegate =
        SupportHelper.findFragment(getChildFragmentManager(), VerticalListDelegate.class);
    final ContentDelegate contentDelegate =
        SupportHelper.findFragment(getChildFragmentManager(), ContentDelegate.class);

    if (verticalListDelegate == null && contentDelegate == null) {
      final VerticalListDelegate delegate = new VerticalListDelegate();
      getSupportDelegate().loadRootFragment(R.id.vertical_list_container, delegate);
      getSupportDelegate().loadRootFragment(R.id.sort_content_container,
          ContentDelegate.newInstance(1));
    }
  }
}
