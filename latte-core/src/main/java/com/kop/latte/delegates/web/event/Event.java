package com.kop.latte.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.delegates.web.WebDelegate;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 15:40
 */
public abstract class Event implements IEvent {

  private Context mContext;
  private String mAction;
  private WebDelegate mDelegate;
  private String mUrl;
  private WebView mWebView;

  public Context getContext() {
    return mContext;
  }

  public void setContext(Context context) {
    mContext = context;
  }

  public String getAction() {
    return mAction;
  }

  public void setAction(String action) {
    mAction = action;
  }

  public LatteDelegate getDelegate() {
    return mDelegate;
  }

  public void setDelegate(WebDelegate delegate) {
    mDelegate = delegate;
  }

  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String url) {
    mUrl = url;
  }

  public WebView getWebView() {
    return mDelegate.getWebView();
  }
}
