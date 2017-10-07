package com.kop.latte.ec.main.index;

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
 * 创建日期: 2017/10/6 17:55
 */
public final class IndexDataConverter extends DataConverter {

  @Override public ArrayList<MultipleItemEntity> convert() {
    ArrayList<MultipleItemEntity> entities = new ArrayList<>();
    final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
    int size = array.size();
    for (int i = 0; i < size; i++) {
      final JSONObject data = array.getJSONObject(i);

      final String imageUrl = data.getString("imageUrl");
      final String text = data.getString("text");
      final int spanSize = data.getInteger("spanSize");
      final int id = data.getInteger("goodsId");
      final JSONArray banners = data.getJSONArray("banners");

      final ArrayList<String> bannerImages = new ArrayList<>();
      int type = 0;
      if (imageUrl == null && text != null) {
        type = ItemType.TEXT;
      } else if (imageUrl != null && text == null) {
        type = ItemType.IMAGE;
      } else if (imageUrl != null) {
        type = ItemType.TEXT_IMAGE;
      } else if (banners != null) {
        type = ItemType.BANNER;
        int bannerSize = banners.size();
        for (int j = 0; j < bannerSize; j++) {
          String banner = banners.getString(j);
          bannerImages.add(banner);
        }
      }

      final MultipleItemEntity entity =
          MultipleItemEntity.builder()
              .setField(MultipleFields.ITEM_TYPE, type)
              .setField(MultipleFields.SPAN_SIZE, spanSize)
              .setField(MultipleFields.ID, id)
              .setField(MultipleFields.TEXT, text)
              .setField(MultipleFields.IMAGE_URL, imageUrl)
              .setField(MultipleFields.BANNERS, bannerImages)
              .build();

      entities.add(entity);
    }
    return entities;
  }
}
