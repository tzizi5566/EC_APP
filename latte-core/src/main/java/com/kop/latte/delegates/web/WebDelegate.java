package com.kop.latte.delegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import com.kop.latte.app.ConfigKeys;
import com.kop.latte.app.Latte;
import com.kop.latte.delegates.LatteDelegate;
import com.kop.latte.delegates.web.route.RouteKeys;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 13:08
 */
public abstract class WebDelegate extends LatteDelegate implements IWebViewInitializer {

  private WebView mWebView;
  private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
  private String mUrl;
  private boolean mIsWebViewAvailable = false;
  private LatteDelegate mTopDelegate;

  public WebDelegate() {
  }

  public abstract IWebViewInitializer setInitializer();

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Bundle bundle = getArguments();
    mUrl = bundle.getString(RouteKeys.URL.name());
    initWebView();
  }

  @SuppressLint("JavascriptInterface") private void initWebView() {
    if (mWebView != null) {
      mWebView.removeAllViews();
      mWebView.destroy();
    } else {
      final IWebViewInitializer initializer = setInitializer();
      if (initializer != null) {
        final WeakReference<WebView> webViewWeakReference =
            new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
        mWebView = webViewWeakReference.get();
        mWebView = initializer.initWebView(mWebView);
        mWebView.setWebViewClient(initializer.initWebViewClient());
        mWebView.setWebChromeClient(initializer.initWebChromeClient());
        final String name = Latte.getConfiguration(ConfigKeys.JAVASCRIPT_INTERFACE);
        mWebView.addJavascriptInterface(LatteWebInterface.create(this), name);
        mIsWebViewAvailable = true;
      } else {
        throw new NullPointerException("Initializer is null");
      }
    }
  }

  public void setTopDelegate(LatteDelegate delegate) {
    mTopDelegate = delegate;
  }

  public LatteDelegate getTopDelegate() {
    if (mTopDelegate == null) {
      mTopDelegate = this;
    }
    return mTopDelegate;
  }

  public WebView getWebView() {
    if (mWebView == null) {
      throw new NullPointerException("WebView is null");
    }
    return mIsWebViewAvailable ? mWebView : null;
  }

  public String getUrl() {
    if (mUrl == null) {
      throw new NullPointerException("url is null");
    }
    return mUrl;
  }

  @Override public void onPause() {
    super.onPause();
    if (mWebView != null) {
      mWebView.onPause();
    }
  }

  @Override public void onResume() {
    super.onResume();
    if (mWebView != null) {
      mWebView.onResume();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mIsWebViewAvailable = false;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (mWebView != null) {
      mWebView.removeAllViews();
      mWebView.destroy();
    }
  }
}
