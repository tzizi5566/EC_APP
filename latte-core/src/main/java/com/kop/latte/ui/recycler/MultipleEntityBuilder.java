package com.kop.latte.ui.recycler;

import java.util.LinkedHashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 17:40
 */
public class MultipleEntityBuilder {

  private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

  public MultipleEntityBuilder() {
    //先清除之前的数据
    FIELDS.clear();
  }

  public final MultipleEntityBuilder setItemType(int itemType) {
    FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
    return this;
  }

  public final MultipleEntityBuilder setField(Object key, Object value) {
    FIELDS.put(key, value);
    return this;
  }

  public final MultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
    FIELDS.putAll(map);
    return this;
  }

  public final MultipleItemEntity build() {
    return new MultipleItemEntity(FIELDS);
  }
}
