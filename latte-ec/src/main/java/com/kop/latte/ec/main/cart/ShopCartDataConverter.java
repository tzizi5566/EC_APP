package com.kop.latte.ec.main.cart;

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
 * 创建日期: 2017/10/28 12:32
 */
public class ShopCartDataConverter extends DataConverter {

  @Override public ArrayList<MultipleItemEntity> convert() {
    ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
    JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
    int size = dataArray.size();
    for (int i = 0; i < size; i++) {
      final JSONObject data = dataArray.getJSONObject(i);
      final String thumb = data.getString("thumb");
      final String desc = data.getString("desc");
      final String title = data.getString("title");
      final int id = data.getInteger("id");
      final int count = data.getInteger("count");
      final double price = data.getDouble("price");

      final MultipleItemEntity entity = MultipleItemEntity.builder()
          .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
          .setField(MultipleFields.ID, id)
          .setField(MultipleFields.IMAGE_URL, thumb)
          .setField(ShopCartItemFields.TITLE, title)
          .setField(ShopCartItemFields.DESC, desc)
          .setField(ShopCartItemFields.COUNT, count)
          .setField(ShopCartItemFields.PRICE, price)
          .setField(ShopCartItemFields.IS_SELECTED, false)
          .setField(ShopCartItemFields.POSITION, i)
          .setField(ShopCartItemFields.TOTAL_PRICE, price * count)
          .build();

      dataList.add(entity);
    }

    return dataList;
  }
}
