package com.kop.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 17:27
 */
public class MultipleItemEntity implements MultiItemEntity {

  private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
  private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
  private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
      new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);

  public MultipleItemEntity(LinkedHashMap<Object, Object> fields) {
    FIELDS_REFERENCE.get().putAll(fields);
  }

  public static MultipleEntityBuilder builder() {
    return new MultipleEntityBuilder();
  }

  @Override public int getItemType() {
    return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
  }

  @SuppressWarnings("unchecked") public final <T> T getField(Object key) {
    return (T) FIELDS_REFERENCE.get().get(key);
  }

  public final LinkedHashMap<?, ?> getFields() {
    return FIELDS_REFERENCE.get();
  }

  public final MultipleItemEntity setField(Object key, Object value) {
    FIELDS_REFERENCE.get().put(key, value);
    return this;
  }
}
