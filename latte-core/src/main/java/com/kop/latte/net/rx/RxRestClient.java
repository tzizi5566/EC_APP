package com.kop.latte.net.rx;

import android.content.Context;
import com.kop.latte.net.HttpMethod;
import com.kop.latte.net.RestCreator;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.ui.loader.LoaderStyle;
import io.reactivex.Observable;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 17:38
 */
public class RxRestClient {

  private final String URL;
  private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
  private final RequestBody BODY;
  private final LoaderStyle LOADER_STYLE;
  private final Context CONTEXT;
  private final File FILE;

  public RxRestClient(String url, Map<String, Object> params, RequestBody body,
      LoaderStyle loaderStyle, Context context, File file) {
    this.URL = url;
    PARAMS.putAll(params);
    this.BODY = body;
    this.LOADER_STYLE = loaderStyle;
    this.CONTEXT = context;
    this.FILE = file;
  }

  public static RxRestClientBuilder builder() {
    return new RxRestClientBuilder();
  }

  private Observable<String> request(HttpMethod method) {
    final RxRestService service = RestCreator.getRxRestService();
    Observable<String> observable = null;

    if (LOADER_STYLE != null) {
      LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
    }

    switch (method) {
      case GET:
        observable = service.get(URL, PARAMS);
        break;

      case POST:
        observable = service.post(URL, PARAMS);
        break;

      case POST_RAW:
        observable = service.postRaw(URL, BODY);
        break;

      case PUT:
        observable = service.put(URL, PARAMS);
        break;

      case PUT_RAW:
        observable = service.putRaw(URL, BODY);
        break;

      case DELETE:
        observable = service.delete(URL, PARAMS);
        break;

      case UPLOAD:
        final RequestBody body =
            RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
        final MultipartBody.Part part =
            MultipartBody.Part.createFormData("file", FILE.getName(), body);
        observable = service.upload(URL, part);
        break;

      default:
        break;
    }

    return observable;
  }

  public final Observable<String> get() {
    return request(HttpMethod.GET);
  }

  public final Observable<String> post() {
    if (BODY == null) {
      return request(HttpMethod.POST);
    } else {
      if (!PARAMS.isEmpty()) {
        throw new RuntimeException("params must be null!");
      }
      return request(HttpMethod.POST_RAW);
    }
  }

  public final Observable<String> put() {
    if (BODY == null) {
      return request(HttpMethod.PUT);
    } else {
      if (!PARAMS.isEmpty()) {
        throw new RuntimeException("params must be null!");
      }
      return request(HttpMethod.PUT_RAW);
    }
  }

  public final Observable<String> delete() {
    return request(HttpMethod.DELETE);
  }

  public final Observable<String> upload() {
    return request(HttpMethod.UPLOAD);
  }

  public final Observable<ResponseBody> download() {
    return RestCreator.getRxRestService().download(URL, PARAMS);
  }
}
