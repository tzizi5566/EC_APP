package com.kop.latte.ui.camera;

import android.net.Uri;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/9 17:14
 */
public final class CameraImageBean {

  private Uri mPath;

  private static final CameraImageBean INSTANCE = new CameraImageBean();

  private CameraImageBean() {
  }

  public static CameraImageBean getInstance() {
    return INSTANCE;
  }

  public Uri getPath() {
    return mPath;
  }

  public void setPath(Uri path) {
    mPath = path;
  }
}
