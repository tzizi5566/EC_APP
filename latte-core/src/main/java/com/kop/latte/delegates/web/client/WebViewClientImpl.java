package com.kop.latte.delegates.web.client;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.kop.latte.app.ConfigKeys;
import com.kop.latte.app.Latte;
import com.kop.latte.delegates.IPageLoadListener;
import com.kop.latte.delegates.web.WebDelegate;
import com.kop.latte.delegates.web.route.Router;
import com.kop.latte.ui.loader.LatteLoader;
import com.kop.latte.util.log.LatteLogger;
import com.kop.latte.util.storage.LattePreference;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 13:49
 */
public class WebViewClientImpl extends WebViewClient {

  private final WebDelegate DELEGATE;
  private IPageLoadListener mIPageLoadListener;

  public void setPageLoadListener(IPageLoadListener pageLoadListener) {
    mIPageLoadListener = pageLoadListener;
  }

  public WebViewClientImpl(WebDelegate delegate) {
    this.DELEGATE = delegate;
  }

  @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
    LatteLogger.d("shouldOverrideUrlLoading", url);
    return Router.getInstance().handleWebUrl(DELEGATE, url);
  }

  @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
    super.onPageStarted(view, url, favicon);
    if (mIPageLoadListener != null) {
      mIPageLoadListener.onLoadStart();
    }
    LatteLoader.showLoading(view.getContext());
  }

  //获取浏览器cookie
  private void syncCookie() {
    final CookieManager cookieManager = CookieManager.getInstance();
    final String webHost = Latte.getConfiguration(ConfigKeys.WEB_HOST);
    if (webHost != null) {
      if (cookieManager.hasCookies()) {
        final String cookieStr = cookieManager.getCookie(webHost);
        if (cookieStr != null && !cookieStr.equals("")) {
          LattePreference.addCustomAppProfile("cookie", cookieStr);
        }
      }
    }
  }

  @Override public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
    syncCookie();
    if (mIPageLoadListener != null) {
      mIPageLoadListener.onLoadEnd();
    }
    LatteLoader.stopLoading();
  }
}
