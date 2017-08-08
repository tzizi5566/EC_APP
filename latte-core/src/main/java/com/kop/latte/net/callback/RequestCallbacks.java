package com.kop.latte.net.callback;

import android.os.Handler;
import android.support.annotation.NonNull;
import com.kop.latte.ui.LatteLoader;
import com.kop.latte.ui.LoaderStyle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 21:18
 */
public class RequestCallbacks implements Callback<String> {

  private final IRequest REQUEST;
  private final ISuccess SUCCESS;
  private final IFailure FAILURE;
  private final IError ERROR;
  private final LoaderStyle LOADER_STYLE;
  private static final Handler HANDLER = new Handler();

  public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error,
      LoaderStyle loaderStyle) {
    this.REQUEST = request;
    this.SUCCESS = success;
    this.FAILURE = failure;
    this.ERROR = error;
    this.LOADER_STYLE = loaderStyle;
  }

  @Override public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
    if (response.isSuccessful()) {
      if (call.isExecuted()) {
        if (SUCCESS != null) {
          SUCCESS.onSuccess(response.body());
        }
      }
    } else {
      if (ERROR != null) {
        ERROR.onError(response.code(), response.message());
      }
    }

    stopLoading();
  }

  @Override public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
    if (FAILURE != null) {
      FAILURE.onFailure();
    }

    if (REQUEST != null) {
      REQUEST.onRequestEnd();
    }

    stopLoading();
  }

  private void stopLoading() {
    if (LOADER_STYLE != null) {
      HANDLER.postDelayed(new Runnable() {
        @Override public void run() {
          LatteLoader.stopLoading();
        }
      }, 2000);
    }
  }
}
