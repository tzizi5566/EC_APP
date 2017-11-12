package com.kop.latte.util.callback;

import android.support.annotation.Nullable;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/11/10 17:37
 */
public interface IGlobalCallback<T> {

  void executeCallback(@Nullable T args);
}
