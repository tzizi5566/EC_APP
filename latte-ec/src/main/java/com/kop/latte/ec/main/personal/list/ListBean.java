package com.kop.latte.ec.main.personal.list;

import android.widget.CompoundButton;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kop.latte.delegates.LatteDelegate;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/6 17:16
 */
public class ListBean implements MultiItemEntity {

  private int mItemType;
  private String mImageUrl;
  private String mText;
  private String mValue;
  private int mId;
  private LatteDelegate mDelegate;
  private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

  public ListBean(int itemType, String imageUrl, String text, String value, int id,
      LatteDelegate delegate,
      CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
    mItemType = itemType;
    mImageUrl = imageUrl;
    mText = text;
    mValue = value;
    mId = id;
    mDelegate = delegate;
    mOnCheckedChangeListener = onCheckedChangeListener;
  }

  @Override public int getItemType() {
    return mItemType;
  }

  public String getImageUrl() {
    return mImageUrl;
  }

  public String getText() {
    if (mText == null) {
      return "";
    }
    return mText;
  }

  public String getValue() {
    if (mValue == null) {
      return "";
    }
    return mValue;
  }

  public int getId() {
    return mId;
  }

  public LatteDelegate getDelegate() {
    return mDelegate;
  }

  public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
    return mOnCheckedChangeListener;
  }

  public static final class Builder {

    private int id;
    private int itemType;
    private String imageUrl;
    private String text;
    private String value;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private LatteDelegate delegate;

    public Builder setId(int id) {
      this.id = id;
      return this;
    }

    public Builder setItemType(int itemType) {
      this.itemType = itemType;
      return this;
    }

    public Builder setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public Builder setText(String text) {
      this.text = text;
      return this;
    }

    public Builder setValue(String value) {
      this.value = value;
      return this;
    }

    public Builder setOnCheckedChangeListener(
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
      this.onCheckedChangeListener = onCheckedChangeListener;
      return this;
    }

    public Builder setDelegate(LatteDelegate delegate) {
      this.delegate = delegate;
      return this;
    }

    public ListBean build() {
      return new ListBean(itemType, imageUrl, text, value, id, delegate, onCheckedChangeListener);
    }
  }
}
