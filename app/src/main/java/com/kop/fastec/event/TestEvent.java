package com.kop.fastec.event;

import android.webkit.WebView;
import android.widget.Toast;
import com.kop.latte.delegates.web.event.Event;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/16 15:36
 */
public class TestEvent extends Event {

  @Override public String execute(String params) {
    Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();
    if (getAction().equals("test")) {
      final WebView webView = getWebView();
      webView.post(new Runnable() {
        @Override public void run() {
          webView.evaluateJavascript("nativeCall();", null);
        }
      });
    }
    return null;
  }
}
