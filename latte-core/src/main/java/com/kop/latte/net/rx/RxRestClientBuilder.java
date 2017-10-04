package com.kop.latte.net.rx;

import android.content.Context;
import com.kop.latte.net.RestCreator;
import com.kop.latte.ui.loader.LoaderStyle;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 20:43
 */
public class RxRestClientBuilder {

  private String mUrl;
  private static final Map<String, Object> PARAMS = RestCreator.getParams();
  private RequestBody mBody = null;
  private LoaderStyle mLoaderStyle = null;
  private Context mContext = null;
  private File mFile = null;

  RxRestClientBuilder() {

  }

  public final RxRestClientBuilder url(String url) {
    this.mUrl = url;
    return this;
  }

  public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
    PARAMS.putAll(params);
    return this;
  }

  public final RxRestClientBuilder params(String key, Object value) {
    PARAMS.put(key, value);
    return this;
  }

  public final RxRestClientBuilder file(File file) {
    this.mFile = file;
    return this;
  }

  public final RxRestClientBuilder file(String file) {
    this.mFile = new File(file);
    return this;
  }

  public final RxRestClientBuilder raw(String raw) {
    this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
    return this;
  }

  public final RxRestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
    this.mContext = context;
    this.mLoaderStyle = loaderStyle;
    return this;
  }

  public final RxRestClientBuilder loader(Context context) {
    this.mContext = context;
    this.mLoaderStyle = LoaderStyle.BallPulseIndicator;
    return this;
  }

  public final RxRestClient build() {
    return new RxRestClient(mUrl, PARAMS, mBody, mLoaderStyle, mContext, mFile);
  }
}
