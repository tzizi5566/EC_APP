package com.kop.latte.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kop.latte.ui.image.GlideApp;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/6 20:23
 */
public class ImageHolder implements Holder<String> {

  private AppCompatImageView mImageView;

  @Override public View createView(Context context) {
    mImageView = new AppCompatImageView(context);
    return mImageView;
  }

  @Override public void UpdateUI(Context context, int position, String data) {
    GlideApp.with(context)
        .load(data)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .into(mImageView);
  }
}
