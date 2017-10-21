package com.kop.latte.ec.main;

import android.graphics.Color;
import com.kop.latte.delegates.bottom.BaseBottomDelegate;
import com.kop.latte.delegates.bottom.BottomItemDelegate;
import com.kop.latte.delegates.bottom.BottomTabBean;
import com.kop.latte.delegates.bottom.ItemBuilder;
import com.kop.latte.ec.main.discover.DiscoverDelegate;
import com.kop.latte.ec.main.index.IndexDelegate;
import com.kop.latte.ec.main.sort.SortDelegate;
import java.util.LinkedHashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 17:39
 */
public class EcBottomDelegate extends BaseBottomDelegate {

  @Override public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
    final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
    items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
    items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
    items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
    items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
    items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
    return builder.addItems(items).build();
  }

  @Override public int setIndexDelegate() {
    return 0;
  }

  @Override public int setClickedColor() {
    return Color.parseColor("#FFFF8800");
  }
}
