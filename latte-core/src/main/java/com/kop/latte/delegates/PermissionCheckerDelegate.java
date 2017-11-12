package com.kop.latte.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.kop.latte.ui.camera.CameraImageBean;
import com.kop.latte.ui.camera.LatteCamera;
import com.kop.latte.ui.camera.RequestCodes;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import com.kop.latte.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 21:10
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

  //不是直接调用方法
  @NeedsPermission(Manifest.permission.CAMERA)
  void startCamera() {
    LatteCamera.start(this);
  }

  //这个是真正调用的方法
  public void startCameraWithCheck() {
    PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
  }

  @OnPermissionDenied(Manifest.permission.CAMERA)
  void onCameraDenied() {
    Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
  }

  @OnNeverAskAgain(Manifest.permission.CAMERA)
  void onCameraNever() {
    Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
  }

  @OnShowRationale(Manifest.permission.CAMERA)
  void onCameraRationale(PermissionRequest request) {
    showRationaleDialog(request);
  }

  private void showRationaleDialog(final PermissionRequest request) {
    new AlertDialog.Builder(getContext())
        .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            request.proceed();
          }
        })
        .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            request.cancel();
          }
        })
        .setCancelable(false)
        .setMessage("权限管理")
        .show();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
        grantResults);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case RequestCodes.TAKE_PHOTO:
          final Uri resultUri = CameraImageBean.getInstance().getPath();
          UCrop.of(resultUri, resultUri)
              .withMaxResultSize(400, 400)
              .start(getContext(), this);
          break;

        case RequestCodes.PICK_PHOTO:
          if (data != null) {
            final Uri pickPath = data.getData();
            //从相册选择后需要有个路径存放剪裁过的图片
            if (pickPath != null) {
              final String pickCropResult = LatteCamera.createCropFile().getPath();
              UCrop.of(pickPath, Uri.parse(pickCropResult))
                  .withMaxResultSize(400, 400)
                  .start(getContext(), this);
            }
          }
          break;

        case RequestCodes.CROP_PHOTO:
          final Uri corpUri = UCrop.getOutput(data);
          //拿到剪裁后的数据进行处理
          @SuppressWarnings("unchecked") final IGlobalCallback<Uri> callback =
              CallbackManager
                  .getInstance()
                  .getCallback(CallbackType.ON_CROP);
          if (callback != null) {
            callback.executeCallback(corpUri);
          }
          break;

        case RequestCodes.CROP_ERROR:
          Toast.makeText(getContext(), "剪裁出错！", Toast.LENGTH_SHORT).show();
          break;

        default:

          break;
      }
    }
  }
}
