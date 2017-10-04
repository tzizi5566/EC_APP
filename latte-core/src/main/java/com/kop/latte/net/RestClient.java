package com.kop.latte.net;

import android.content.Context;
import com.kop.latte.net.callback.IError;
import com.kop.latte.net.callback.IFailure;
import com.kop.latte.net.callback.IRequest;
import com.kop.latte.net.callback.ISuccess;
import com.kop.latte.net.callback.RequestCallbacks;
import com.kop.latte.net.download.DownloadHandler;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.ui.loader.LoaderStyle;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
  private final File FILE;
  private final String DOWNLOAD_DIR;
  private final String EXTENSION;
  private final String NAME;

  public RestClient(String url, Map<String, Object> params,
      IRequest request, ISuccess success, IFailure failure, IError error, RequestBody body,
      LoaderStyle loaderStyle, Context context, File file, String downloadDir, String extension,
      String name) {
    this.URL = url;
    PARAMS.putAll(params);
    this.REQUEST = request;
    this.SUCCESS = success;
    this.FAILURE = failure;
    this.ERROR = error;
    this.BODY = body;
    this.LOADER_STYLE = loaderStyle;
    this.CONTEXT = context;
    this.FILE = file;
    this.DOWNLOAD_DIR = downloadDir;
    this.EXTENSION = extension;
    this.NAME = name;
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

      case POST_RAW:
        call = service.postRaw(URL, BODY);
        break;

      case PUT:
        call = service.put(URL, PARAMS);
        break;

      case PUT_RAW:
        call = service.putRaw(URL, BODY);
        break;

      case DELETE:
        call = service.delete(URL, PARAMS);
        break;

      case UPLOAD:
        final RequestBody body =
            RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
        final MultipartBody.Part part =
            MultipartBody.Part.createFormData("file", FILE.getName(), body);
        call = RestCreator.getRestService().upload(URL, part);
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
    if (BODY == null) {
      request(HttpMethod.POST);
    } else {
      if (!PARAMS.isEmpty()) {
        throw new RuntimeException("params must be null!");
      }
      request(HttpMethod.POST_RAW);
    }
  }

  public final void put() {
    if (BODY == null) {
      request(HttpMethod.PUT);
    } else {
      if (!PARAMS.isEmpty()) {
        throw new RuntimeException("params must be null!");
      }
      request(HttpMethod.PUT_RAW);
    }
  }

  public final void delete() {
    request(HttpMethod.DELETE);
  }

  public final void upload() {
    request(HttpMethod.UPLOAD);
  }

  public final void download() {
    new DownloadHandler(URL, REQUEST, SUCCESS, FAILURE, ERROR, DOWNLOAD_DIR, EXTENSION,
        NAME).handleDownload();
  }
}
