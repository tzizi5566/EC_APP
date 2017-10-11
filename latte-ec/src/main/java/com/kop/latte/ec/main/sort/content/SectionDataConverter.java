package com.kop.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/9 12:49
 */
public class SectionDataConverter {

  final List<SectionBean> convert(String json) {
    final List<SectionBean> dataList = new ArrayList<>();
    final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");

    int size = dataArray.size();
    for (int i = 0; i < size; i++) {
      final JSONObject data = dataArray.getJSONObject(i);
      final int id = data.getInteger("id");
      final String title = data.getString("section");

      //添加title
      final SectionBean sectionTitleBean = new SectionBean(true, title);
      sectionTitleBean.setId(id);
      sectionTitleBean.setMore(true);
      dataList.add(sectionTitleBean);

      final JSONArray goods = data.getJSONArray("goods");
      //商品内容循环
      int goodsSize = goods.size();
      for (int j = 0; j < goodsSize; j++) {
        final JSONObject contentItem = goods.getJSONObject(j);
        final int goodsId = contentItem.getInteger("goods_id");
        final String goodsName = contentItem.getString("goods_name");
        final String goodsThumb = contentItem.getString("goods_thumb");
        //获取内容
        final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
        itemEntity.setGoodsId(goodsId);
        itemEntity.setGoodsName(goodsName);
        itemEntity.setGoodsThumb(goodsThumb);

        //添加内容
        dataList.add(new SectionBean(itemEntity));
      }
    }
    return dataList;
  }
}
