package com.kop.latte.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 13:18
 */
public interface IWebViewInitializer {

  WebView initWebView(WebView webView);

  WebViewClient initWebViewClient();

  WebChromeClient initWebChromeClient();
}
