package com.kop.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.kop.latte.ec.R;
import com.kop.latte.ec.main.sort.SortDelegate;
import com.kop.latte.ec.main.sort.content.ContentDelegate;
import com.kop.latte.ui.recycler.ItemType;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.ui.recycler.MultipleRecyclerAdapter;
import com.kop.latte.ui.recycler.MultipleViewHolder;
import java.util.List;
import java.util.Random;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/8 20:13
 */
public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

  private final SortDelegate DELEGATE;
  private int mPrePosition = 0;
  private final Random mRandom;

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate sortDelegate) {
    super(data);
    this.DELEGATE = sortDelegate;
    //添加垂直菜单布局
    addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    mRandom = new Random();
  }

  @Override
  protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
    super.convert(holder, entity);
    switch (holder.getItemViewType()) {
      case ItemType.VERTICAL_MENU_LIST:
        final String text = entity.getField(MultipleFields.TEXT);
        final boolean isClicked = entity.getField(MultipleFields.TAG);
        final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
        final View line = holder.getView(R.id.view_line);
        final View itemView = holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            final int currentPosition = holder.getAdapterPosition();
            if (mPrePosition != currentPosition) {
              //还原上一个
              getData().get(mPrePosition).setField(MultipleFields.TAG, false);
              notifyItemChanged(mPrePosition);

              //更新选中的item
              entity.setField(MultipleFields.TAG, true);
              notifyItemChanged(currentPosition);
              mPrePosition = currentPosition;

              final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
              int i = mRandom.nextInt(2) + 1;
              switchCpntent(i);
            }
          }
        });

        if (!isClicked) {
          line.setVisibility(View.INVISIBLE);
          name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
          itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
        } else {
          line.setVisibility(View.VISIBLE);
          name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
          line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
          itemView.setBackgroundColor(Color.WHITE);
        }

        holder.setText(R.id.tv_vertical_item_name, text);
        break;

      default:
        break;
    }
  }

  //private void showContent(int contentId) {
  //  final ContentDelegate contentDelegate = ContentDelegate.newInstance(contentId);
  //  switchCpntent(contentDelegate);
  //}

  private void switchCpntent(int contentId) {
    final ContentDelegate contentDelegate = DELEGATE.findChildFragment(ContentDelegate.class);
    if (contentDelegate != null) {
      contentDelegate.initData(contentId);
    }
  }
}
