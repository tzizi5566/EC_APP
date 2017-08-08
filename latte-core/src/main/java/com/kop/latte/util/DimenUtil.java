package com.kop.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.kop.latte.app.Latte;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 22:06
 */
public class DimenUtil {

  public static int getScreenWidth() {
    final Resources resources = Latte.getApplication().getResources();
    final DisplayMetrics dm = resources.getDisplayMetrics();
    return dm.widthPixels;
  }

  public static int getScreenHeight() {
    final Resources resources = Latte.getApplication().getResources();
    final DisplayMetrics dm = resources.getDisplayMetrics();
    return dm.heightPixels;
  }
}
