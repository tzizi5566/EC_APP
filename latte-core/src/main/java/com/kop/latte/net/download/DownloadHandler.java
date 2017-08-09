package com.kop.latte.net.download;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.kop.latte.net.RestCreator;
import com.kop.latte.net.callback.IError;
import com.kop.latte.net.callback.IFailure;
import com.kop.latte.net.callback.IRequest;
import com.kop.latte.net.callback.ISuccess;
import java.util.WeakHashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/9 19:25
 */
public class DownloadHandler {

  private final String URL;
  private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
  private final IRequest REQUEST;
  private final ISuccess SUCCESS;
  private final IFailure FAILURE;
  private final IError ERROR;
  private final String DOWNLOAD_DIR;
  private final String EXTENSION;
  private final String NAME;

  public DownloadHandler(String url, IRequest request, ISuccess success,
      IFailure failure, IError error, String downloadDir, String extension, String name) {
    this.URL = url;
    this.REQUEST = request;
    this.SUCCESS = success;
    this.FAILURE = failure;
    this.ERROR = error;
    this.DOWNLOAD_DIR = downloadDir;
    this.EXTENSION = extension;
    this.NAME = name;
  }

  public final void handleDownload() {
    if (REQUEST != null) {
      REQUEST.onRequestStart();
    }

    RestCreator.getRestService().download(URL, PARAMS).enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(@NonNull Call<ResponseBody> call,
          @NonNull Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          final SaveFileTask saveFileTask = new SaveFileTask(REQUEST, SUCCESS);
          final ResponseBody body = response.body();
          saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION,
              body, NAME);

          //这里一定要注意判断，否则文件下载不全
          if (saveFileTask.isCancelled()) {
            if (REQUEST != null) {
              REQUEST.onRequestEnd();
            }
          }
        } else {
          if (ERROR != null) {
            ERROR.onError(response.code(), response.message());
          }
        }
      }

      @Override public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
        if (FAILURE != null) {
          FAILURE.onFailure();
        }
      }
    });
  }
}
