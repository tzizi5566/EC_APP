package com.kop.latte.util.callback;

import java.util.WeakHashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/10 17:37
 */
public class CallbackManager {

  private static final WeakHashMap<Object, IGlobalCallback> CALLBACKS = new WeakHashMap<>();

  private static class Holder {
    private static final CallbackManager INSTANCE = new CallbackManager();
  }

  public static CallbackManager getInstance() {
    return Holder.INSTANCE;
  }

  public CallbackManager addCallback(Object tag, IGlobalCallback callback) {
    CALLBACKS.put(tag, callback);
    return this;
  }

  public IGlobalCallback getCallback(Object tag) {
    return CALLBACKS.get(tag);
  }
}
