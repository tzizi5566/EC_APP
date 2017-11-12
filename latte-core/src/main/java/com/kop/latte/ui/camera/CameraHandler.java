package com.kop.latte.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.blankj.utilcode.util.FileUtils;
import com.kop.latte.R;
import com.kop.latte.app.Latte;
import com.kop.latte.delegates.PermissionCheckerDelegate;
import com.kop.latte.util.file.FileUtil;
import java.io.File;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/9 17:11
 */
public class CameraHandler implements View.OnClickListener {

  private final AlertDialog DIALOG;
  private final PermissionCheckerDelegate DELEGATE;

  public CameraHandler(PermissionCheckerDelegate delegate) {
    this.DELEGATE = delegate;
    DIALOG = new AlertDialog.Builder(delegate.getContext()).create();
  }

  final void beginCameraDialog() {
    DIALOG.show();
    final Window window = DIALOG.getWindow();
    if (window != null) {
      window.setContentView(R.layout.dialog_camera_panel);
      window.setGravity(Gravity.BOTTOM);
      window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
      window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      //设置属性
      final WindowManager.LayoutParams params = window.getAttributes();
      params.width = WindowManager.LayoutParams.MATCH_PARENT;
      params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
      params.dimAmount = 0.5f;
      window.setAttributes(params);

      window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
      window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
      window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
    }
  }

  private String getPhotoName() {
    return FileUtil.getFileNameByTime("IMG", "jpg");
  }

  private void takePhoto() {
    final String currentPhotoName = getPhotoName();
    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);

    //兼容7.0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      final ContentValues contentValues = new ContentValues(1);
      contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
      final Uri uri = DELEGATE.getContext()
          .getContentResolver()
          .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
      //需要将Uri路径转化为真实路劲
      final File realFile =
          FileUtils.getFileByPath(FileUtil.getRealFilePath(Latte.getApplicationContext(), uri));
      final Uri realUri = Uri.fromFile(realFile);
      CameraImageBean.getInstance().setPath(realUri);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    } else {
      final Uri uri = Uri.fromFile(tempFile);
      CameraImageBean.getInstance().setPath(uri);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }

    DELEGATE.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
  }

  private void pickPhoto() {
    final Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    DELEGATE.startActivityForResult(Intent.createChooser(intent, "选择获取图片的方式"),
        RequestCodes.PICK_PHOTO);
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.photodialog_btn_cancel) {
      DIALOG.cancel();
    } else if (id == R.id.photodialog_btn_take) {
      takePhoto();
      DIALOG.cancel();
    } else if (id == R.id.photodialog_btn_native) {
      pickPhoto();
      DIALOG.cancel();
    }
  }
}
