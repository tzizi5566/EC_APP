package com.kop.latte.net;

import android.content.Context;
import com.kop.latte.net.callback.IError;
import com.kop.latte.net.callback.IFailure;
import com.kop.latte.net.callback.IRequest;
import com.kop.latte.net.callback.ISuccess;
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
public class RestClientBuilder {

  private String mUrl;
  private static final Map<String, Object> PARAMS = RestCreator.getParams();
  private IRequest mRequest = null;
  private ISuccess mSuccess = null;
  private IFailure mFailure = null;
  private IError mError = null;
  private RequestBody mBody = null;
  private LoaderStyle mLoaderStyle = null;
  private Context mContext = null;
  private File mFile = null;
  private String mDownloadDir = null;
  private String mExtension = null;
  private String mName = null;

  RestClientBuilder() {

  }

  public final RestClientBuilder url(String url) {
    this.mUrl = url;
    return this;
  }

  public final RestClientBuilder params(WeakHashMap<String, Object> params) {
    PARAMS.putAll(params);
    return this;
  }

  public final RestClientBuilder params(String key, Object value) {
    PARAMS.put(key, value);
    return this;
  }

  public final RestClientBuilder file(File file) {
    this.mFile = file;
    return this;
  }

  public final RestClientBuilder file(String file) {
    this.mFile = new File(file);
    return this;
  }

  public final RestClientBuilder raw(String raw) {
    this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
    return this;
  }

  public final RestClientBuilder onRequest(IRequest request) {
    this.mRequest = request;
    return this;
  }

  public final RestClientBuilder success(ISuccess success) {
    this.mSuccess = success;
    return this;
  }

  public final RestClientBuilder failure(IFailure failure) {
    this.mFailure = failure;
    return this;
  }

  public final RestClientBuilder error(IError error) {
    this.mError = error;
    return this;
  }

  public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
    this.mContext = context;
    this.mLoaderStyle = loaderStyle;
    return this;
  }

  public final RestClientBuilder loader(Context context) {
    this.mContext = context;
    this.mLoaderStyle = LoaderStyle.BallPulseIndicator;
    return this;
  }

  public final RestClientBuilder downloadDir(String downloadDir) {
    this.mDownloadDir = downloadDir;
    return this;
  }

  public final RestClientBuilder extension(String extension) {
    this.mExtension = extension;
    return this;
  }

  public final RestClientBuilder name(String name) {
    this.mName = name;
    return this;
  }

  public final RestClient build() {
    return new RestClient(mUrl, PARAMS, mRequest, mSuccess, mFailure, mError, mBody, mLoaderStyle,
        mContext, mFile, mDownloadDir, mExtension, mName);
  }
}
