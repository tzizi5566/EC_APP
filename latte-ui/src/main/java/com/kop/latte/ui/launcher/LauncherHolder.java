package com.kop.latte.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/10 21:46
 */
public class LauncherHolder implements Holder<Integer> {

  private AppCompatImageView mImageView = null;

  @Override public View createView(Context context) {
    mImageView = new AppCompatImageView(context);
    return mImageView;
  }

  @Override public void UpdateUI(Context context, int position, Integer data) {
    mImageView.setBackgroundResource(data);
  }
}
