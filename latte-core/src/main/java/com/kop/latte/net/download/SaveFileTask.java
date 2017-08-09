package com.kop.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import com.kop.latte.app.Latte;
import com.kop.latte.net.callback.IRequest;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.util.file.FileUtil;
import java.io.File;
import java.io.InputStream;
import okhttp3.ResponseBody;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/9 19:31
 */
public class SaveFileTask extends AsyncTask<Object, Void, File> {

  private final IRequest REQUEST;
  private final ISuccess SUCCESS;

  public SaveFileTask(IRequest request, ISuccess success) {
    this.REQUEST = request;
    this.SUCCESS = success;
  }

  @Override protected File doInBackground(Object... params) {
    String downloadDir = (String) params[0];
    String extension = (String) params[1];
    final ResponseBody body = (ResponseBody) params[2];
    final String name = (String) params[3];
    final InputStream is = body.byteStream();
    if (downloadDir == null || downloadDir.equals("")) {
      downloadDir = "down_loads";
    }
    if (extension == null || extension.equals("")) {
      extension = "";
    }
    if (name == null) {
      return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
    } else {
      return FileUtil.writeToDisk(is, downloadDir, name);
    }
  }

  @Override protected void onPostExecute(File file) {
    super.onPostExecute(file);
    if (SUCCESS != null) {
      SUCCESS.onSuccess(file.getPath());
    }
    if (REQUEST != null) {
      REQUEST.onRequestEnd();
    }

    autoInstallApk(file);
  }

  private void autoInstallApk(File file) {
    if (FileUtil.getExtension(file.getPath()).equals("apk")) {
      final Intent intent = new Intent();
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setAction(Intent.ACTION_VIEW);
      intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
      Latte.getApplicationContext().startActivity(intent);
    }
  }
}
