package com.kop.latte.delegates.web;

import android.webkit.JavascriptInterface;
import com.alibaba.fastjson.JSON;
import com.kop.latte.delegates.web.event.Event;
import com.kop.latte.delegates.web.event.EventManager;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/13 13:28
 */
final class LatteWebInterface {

  private final WebDelegate DELEGATE;

  private LatteWebInterface(WebDelegate delegate) {
    this.DELEGATE = delegate;
  }

  static LatteWebInterface create(WebDelegate webDelegate) {
    return new LatteWebInterface(webDelegate);
  }

  @JavascriptInterface
  public String event(String params) {
    final String action = JSON.parseObject(params).getString("action");
    final Event event = EventManager.getInstance().createEvent(action);
    if (event != null) {
      event.setAction(action);
      event.setDelegate(DELEGATE);
      event.setContext(DELEGATE.getContext());
      event.setUrl(DELEGATE.getUrl());
      return event.execute(params);
    }
    return null;
  }
}
