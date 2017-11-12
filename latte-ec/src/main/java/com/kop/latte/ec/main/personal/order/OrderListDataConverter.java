package com.kop.latte.ec.main.personal.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kop.latte.ui.recycler.DataConverter;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/8 15:46
 */
public class OrderListDataConverter extends DataConverter {

  @Override public ArrayList<MultipleItemEntity> convert() {
    final JSONArray array = JSONArray.parseObject(getJsonData()).getJSONArray("data");
    int size = array.size();
    for (int i = 0; i < size; i++) {
      final JSONObject data = array.getJSONObject(i);
      final String thumb = data.getString("thumb");
      final String title = data.getString("title");
      final String time  = data.getString("time");
      final int id = data.getInteger("id");
      final double price = data.getDouble("price");

      final MultipleItemEntity entity = MultipleItemEntity.builder()
          .setItemType(OrderListItemType.ITEM_ORDER_LIST)
          .setField(MultipleFields.ID, id)
          .setField(MultipleFields.IMAGE_URL, thumb)
          .setField(MultipleFields.TITLE, title)
          .setField(OrderItemFields.PRICE, price)
          .setField(OrderItemFields.TIME, time)
          .build();

      ENTITIES.add(entity);
    }
    return ENTITIES;
  }
}
