package com.kop.latte.ec.main.index.search;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.kop.latte.ui.recycler.DataConverter;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import com.kop.latte.util.storage.LattePreference;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/16 16:14
 */
public class SearchDataConverter extends DataConverter {

  static final String TAG_SEARCH_HISTORY = "search_history";

  @Override public ArrayList<MultipleItemEntity> convert() {
    final String jsonString = LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
    if (!TextUtils.isEmpty(jsonString) && !jsonString.equals("")) {
      final JSONArray array = JSONArray.parseArray(jsonString);
      int size = array.size();
      for (int i = 0; i < size; i++) {
        final String text = array.getString(i);
        final MultipleItemEntity entity = MultipleItemEntity.builder()
            .setItemType(ScarchItemType.ITEM_SEARCH)
            .setField(MultipleFields.TEXT, text)
            .build();

        ENTITIES.add(entity);
      }
    }
    return ENTITIES;
  }
}
