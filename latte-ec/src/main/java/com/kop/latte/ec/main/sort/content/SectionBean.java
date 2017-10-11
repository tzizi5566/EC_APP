package com.kop.latte.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/9 12:50
 */
public class SectionBean extends SectionEntity<SectionContentItemEntity> {

  private boolean mIsMore = false;
  private int mId = -1;

  public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
    super(sectionContentItemEntity);
  }

  public SectionBean(boolean isHeader, String header) {
    super(isHeader, header);
  }

  public boolean isMore() {
    return mIsMore;
  }

  public void setMore(boolean more) {
    mIsMore = more;
  }

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    mId = id;
  }
}
