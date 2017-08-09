package com.kop.latte.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import com.kop.latte.util.file.FileUtil;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/9 21:12
 */
public class DebugInterceptor extends BaseInterceptor {

  private final String DEBUG_URL;
  private final int DEBUG_RAW_ID;

  public DebugInterceptor(String url, int rawId) {
    this.DEBUG_URL = url;
    this.DEBUG_RAW_ID = rawId;
  }

  private Response getResponse(Chain chain, String json) {
    return new Response.Builder().code(200)
        .addHeader("Content-Type", "application/json")
        .body(ResponseBody.create(MediaType.parse("application/json"), json))
        .message("OK")
        .request(chain.request())
        .protocol(Protocol.HTTP_1_1)
        .build();
  }

  private Response debugResponse(Chain chain, @RawRes int rawId) {
    final String json = FileUtil.getRawFile(rawId);
    return getResponse(chain, json);
  }

  @Override public Response intercept(@NonNull Chain chain) throws IOException {
    final String url = chain.request().url().toString();
    if (url.contains(DEBUG_URL)) {
      return debugResponse(chain, DEBUG_RAW_ID);
    }
    return chain.proceed(chain.request());
  }
}
