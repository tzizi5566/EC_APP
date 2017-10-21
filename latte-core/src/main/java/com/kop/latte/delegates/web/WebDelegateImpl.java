package com.kop.latte.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.kop.latte.delegates.IPageLoadListener;
import com.kop.latte.delegates.web.chromeClient.WebChromeClientImpl;
import com.kop.latte.delegates.web.client.WebViewClientImpl;
import com.kop.latte.delegates.web.route.RouteKeys;
import com.kop.latte.delegates.web.route.Router;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 13:36
 */
public class WebDelegateImpl extends WebDelegate {

  private IPageLoadListener mIPageLoadListener;

  public void setPageLoadListener(IPageLoadListener pageLoadListener) {
    mIPageLoadListener = pageLoadListener;
  }

  public static WebDelegateImpl create(String url) {
    final Bundle bundle = new Bundle();
    bundle.putString(RouteKeys.URL.name(), url);
    final WebDelegateImpl delegate = new WebDelegateImpl();
    delegate.setArguments(bundle);
    return delegate;
  }

  @Override public Object setLayout() {
    return getWebView();
  }

  @Override public void onBindView(@Nullable Bundle savedInstanceState, @Nullable View rootView) {
    if (getUrl() != null) {
      //用原生的方式模拟web跳转
      Router.getInstance().loadPage(this, getUrl());
    }
  }

  @Override public IWebViewInitializer setInitializer() {
    return this;
  }

  @Override public WebView initWebView(WebView webView) {
    return new WebViewInitializer().createWebView(webView);
  }

  @Override public WebViewClient initWebViewClient() {
    final WebViewClientImpl client = new WebViewClientImpl(this);
    client.setPageLoadListener(mIPageLoadListener);
    return client;
  }

  @Override public WebChromeClient initWebChromeClient() {
    return new WebChromeClientImpl();
  }
}
