package com.kop.latte.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.joanzapata.iconify.widget.IconTextView;
import com.kop.latte.R;
import com.kop.latte.R2;
import com.kop.latte.delegates.LatteDelegate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 15:58
 */
public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

  @BindView(R2.id.bottom_bar) LinearLayoutCompat mBottomBar;

  private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
  private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();
  private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
  private int mCurrentDelegate = 0;
  private int mIndexDelegate = 0;
  private int mClickedColor = Color.RED;

  public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

  public abstract int setIndexDelegate();

  @ColorInt
  public abstract int setClickedColor();

  @Override public Object setLayout() {
    return R.layout.delegate_bottom;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mIndexDelegate = setIndexDelegate();
    if (setClickedColor() != 0) {
      mClickedColor = setClickedColor();
    }

    final ItemBuilder builder = ItemBuilder.builder();
    final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
    ITEMS.putAll(items);

    for (Map.Entry<BottomTabBean, BottomItemDelegate> entry : ITEMS.entrySet()) {
      final BottomTabBean key = entry.getKey();
      final BottomItemDelegate value = entry.getValue();

      TAB_BEANS.add(key);
      ITEM_DELEGATE.add(value);
    }
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    int size = ITEMS.size();
    for (int i = 0; i < size; i++) {
      LayoutInflater.from(getContext())
          .inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
      final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
      //设置每个item的点击事件
      item.setTag(i);
      item.setOnClickListener(this);
      final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
      final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
      BottomTabBean bean = TAB_BEANS.get(i);
      //初始化数据
      itemIcon.setText(bean.getIcon());
      itemTitle.setText(bean.getTitle());
      if (i == mIndexDelegate) {
        itemIcon.setTextColor(mClickedColor);
        itemTitle.setTextColor(mClickedColor);
      }
    }

    final SupportFragment[] delegateArray = ITEM_DELEGATE.toArray(new SupportFragment[size]);
    loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
  }

  private void resetColor() {
    int count = mBottomBar.getChildCount();
    for (int i = 0; i < count; i++) {
      final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
      final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
      final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
      itemIcon.setTextColor(Color.GRAY);
      itemTitle.setTextColor(Color.GRAY);
    }
  }

  @Override public void onClick(View v) {
    final int tag = (int) v.getTag();
    resetColor();
    final RelativeLayout item = (RelativeLayout) v;
    final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
    final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
    itemIcon.setTextColor(mClickedColor);
    itemTitle.setTextColor(mClickedColor);
    showHideFragment(ITEM_DELEGATE.get(tag), ITEM_DELEGATE.get(mCurrentDelegate));
    //注意先后顺序
    mCurrentDelegate = tag;
  }
}
