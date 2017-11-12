package com.kop.latte.ui.camera;

import android.net.Uri;
import com.kop.latte.delegates.PermissionCheckerDelegate;
import com.kop.latte.util.file.FileUtil;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/9 17:13
 */
public class LatteCamera {

  public static Uri createCropFile() {
    return Uri.parse(FileUtil.createFile("crop_image",
        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
  }

  public static void start(PermissionCheckerDelegate delegate) {
    new CameraHandler(delegate).beginCameraDialog();
  }
}
