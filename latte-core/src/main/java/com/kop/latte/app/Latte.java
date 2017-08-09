package com.kop.latte.app;

import android.content.Context;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 19:44
 */
public final class Latte {

  public static Configurator init(Context context) {
    Configurator.getInstance()
        .getLatteConfigs()
        .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
    return Configurator.getInstance();
  }

  public static Configurator getConfigurator() {
    return Configurator.getInstance();
  }

  public static <T> T getConfiguration(Object key) {
    return getConfigurator().getConfiguration(key);
  }

  public static Context getApplicationContext() {
    return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
  }
}
