package com.kop.latte.ui.recycler;

import java.util.ArrayList;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 17:26
 */
public abstract class DataConverter {

  private String mJsonData = null;

  public abstract ArrayList<MultipleItemEntity> convert();

  public DataConverter setJsonData(String json) {
    this.mJsonData = json;
    return this;
  }

  protected String getJsonData() {
    if (mJsonData == null || mJsonData.isEmpty()) {
      throw new NullPointerException("DATA IS NULL!");
    }
    return mJsonData;
  }
}
