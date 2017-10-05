package com.kop.latte.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/5 16:25
 */
public final class ItemBuilder {

  private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

  private ItemBuilder() {
  }

  static ItemBuilder builder() {
    return new ItemBuilder();
  }

  public final ItemBuilder addItem(BottomTabBean tabBean, BottomItemDelegate itemDelegate) {
    ITEMS.put(tabBean, itemDelegate);
    return this;
  }

  public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
    ITEMS.putAll(items);
    return this;
  }

  public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
    return ITEMS;
  }
}
