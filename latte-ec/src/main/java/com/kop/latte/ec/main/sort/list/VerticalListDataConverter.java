package com.kop.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kop.latte.ui.recycler.DataConverter;
import com.kop.latte.ui.recycler.ItemType;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/8 17:57
 */
public class VerticalListDataConverter extends DataConverter {

  @Override public ArrayList<MultipleItemEntity> convert() {
    final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
    final JSONArray jsonArray =
        JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("list");
    int size = jsonArray.size();
    for (int i = 0; i < size; i++) {
      final JSONObject data = jsonArray.getJSONObject(i);
      final int id = data.getInteger("id");
      final String name = data.getString("name");

      final MultipleItemEntity entity = MultipleItemEntity.builder()
          .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
          .setField(MultipleFields.ID, id)
          .setField(MultipleFields.TEXT, name)
          .setField(MultipleFields.TAG, false)
          .build();

      dataList.add(entity);
    }
    //设置第一个被选中
    dataList.get(0).setField(MultipleFields.TAG, true);
    return dataList;
  }
}
