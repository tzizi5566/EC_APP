package com.kop.latte.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kop.latte.ui.recycler.DataConverter;
import com.kop.latte.ui.recycler.MultipleFields;
import com.kop.latte.ui.recycler.MultipleItemEntity;
import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/11 15:15
 */
public class AddressDataConverter extends DataConverter {

  @Override public ArrayList<MultipleItemEntity> convert() {
    final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
    int size = array.size();
    for (int i = 0; i < size; i++) {
      final JSONObject data = array.getJSONObject(i);
      final int id = data.getInteger("id");
      final String name = data.getString("name");
      final String phone = data.getString("phone");
      final String address = data.getString("address");
      final boolean isDefault = data.getBoolean("default");

      final MultipleItemEntity entity = MultipleItemEntity.builder()
          .setItemType(AddressItemType.ITEM_ADDRESS)
          .setField(MultipleFields.ID, id)
          .setField(MultipleFields.NAME, name)
          .setField(MultipleFields.TAG, isDefault)
          .setField(AddressItemFields.PHONE, phone)
          .setField(AddressItemFields.ADDRESS, address)
          .build();

      ENTITIES.add(entity);
    }
    return ENTITIES;
  }
}
