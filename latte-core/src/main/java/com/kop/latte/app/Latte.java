package com.kop.latte.app;

import android.content.Context;
import java.util.HashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 19:44
 */
public final class Latte {

  public static Configurator init(Context context) {
    getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
    return Configurator.getInstance();
  }

  private static HashMap<String, Object> getConfigurations() {
    return Configurator.getInstance().getLatteConfigs();
  }

  public static Context getApplication() {
    return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
  }
}
