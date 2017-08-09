package com.kop.latte.net.interceptors;

import java.util.LinkedHashMap;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/9 21:00
 */
public abstract class BaseInterceptor implements Interceptor {

  protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
    final HttpUrl url = chain.request().url();
    int size = url.querySize();
    final LinkedHashMap<String, String> params = new LinkedHashMap<>();
    for (int i = 0; i < size; i++) {
      params.put(url.queryParameterName(i), url.queryParameterValue(i));
    }
    return params;
  }

  protected String getUrlParameters(Chain chain, String key) {
    final Request request = chain.request();
    return request.url().queryParameter(key);
  }

  protected LinkedHashMap<String, String> getBodyParamers(Chain chain) {
    final FormBody formBody = (FormBody) chain.request().body();
    final LinkedHashMap<String, String> params = new LinkedHashMap<>();
    int size = formBody.size();
    for (int i = 0; i < size; i++) {
      params.put(formBody.encodedName(i), formBody.encodedValue(i));
    }
    return params;
  }

  protected String getBodyParamers(Chain chain, String key) {
    return getBodyParamers(chain).get(key);
  }
}
