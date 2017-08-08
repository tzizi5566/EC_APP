package com.kop.latte.net;

import android.content.Context;
import com.kop.latte.net.callback.IError;
import com.kop.latte.net.callback.IFailure;
import com.kop.latte.net.callback.IRequest;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.net.callback.RequestCallbacks;
import com.kop.latte.ui.LatteLoader;
import com.kop.latte.ui.LoaderStyle;
import java.util.Map;
import java.util.WeakHashMap;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 17:38
 */
public class RestClient {

  private final String URL;
  private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
  private final IRequest REQUEST;
  private final ISuccess SUCCESS;
  private final IFailure FAILURE;
  private final IError ERROR;
  private final RequestBody BODY;
  private final LoaderStyle LOADER_STYLE;
  private final Context CONTEXT;

  public RestClient(String url, Map<String, Object> params,
      IRequest request, ISuccess success, IFailure failure, IError error, RequestBody body,
      LoaderStyle loaderStyle, Context context) {
    this.URL = url;
    PARAMS.putAll(params);
    this.REQUEST = request;
    this.SUCCESS = success;
    this.FAILURE = failure;
    this.ERROR = error;
    this.BODY = body;
    this.LOADER_STYLE = loaderStyle;
    this.CONTEXT = context;
  }

  public static RestClientBuilder builder() {
    return new RestClientBuilder();
  }

  private void request(HttpMethod method) {
    final RestService service = RestCreator.getRestService();
    Call<String> call = null;
    if (REQUEST != null) {
      REQUEST.onRequestStart();
    }

    if (LOADER_STYLE != null) {
      LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
    }

    switch (method) {
      case GET:
        call = service.get(URL, PARAMS);
        break;

      case POST:
        call = service.post(URL, PARAMS);
        break;

      case PUT:
        call = service.put(URL, PARAMS);
        break;

      case DELETE:
        call = service.delete(URL, PARAMS);
        break;

      default:
        break;
    }

    if (call != null) {
      call.enqueue(getRequestCallback());
    }
  }

  private Callback<String> getRequestCallback() {
    return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
  }

  public final void get() {
    request(HttpMethod.GET);
  }

  public final void post() {
    request(HttpMethod.POST);
  }

  public final void put() {
    request(HttpMethod.PUT);
  }

  public final void delete() {
    request(HttpMethod.DELETE);
  }
}
