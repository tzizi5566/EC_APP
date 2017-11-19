package com.kop.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;
import com.kop.latte.ec.R;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.ui.recycler.MultipleViewHolder;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/16 16:24
 */
public class SearchAdapter extends MultipleRecyclerAdapter {

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  SearchAdapter(List<MultipleItemEntity> data) {
    super(data);
    addItemType(ScarchItemType.ITEM_SEARCH, R.layout.item_search);
  }

  @Override protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
    super.convert(holder, entity);
    switch (entity.getItemType()) {
      case ScarchItemType.ITEM_SEARCH:
        final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
        final String history = entity.getField(MultipleFields.TEXT);
        tvSearchItem.setText(history);
        break;

      default:

        break;
    }
  }
}
