package com.kop.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import java.util.ArrayList;
import java.util.HashMap;
import okhttp3.Interceptor;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 19:48
 */
public class Configurator {

  private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
  private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
  private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

  private Configurator() {
    LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
  }

  public static Configurator getInstance() {
    return Holder.INSTANCE;
  }

  final HashMap<Object, Object> getLatteConfigs() {
    return LATTE_CONFIGS;
  }

  private static class Holder {
    private static final Configurator INSTANCE = new Configurator();
  }

  public final void configure() {
    initIcons();
    LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
  }

  public final Configurator withApiHost(String host) {
    LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
    return this;
  }

  private void initIcons() {
    if (ICONS.size() > 0) {
      final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
      for (int i = 1; i < ICONS.size(); i++) {
        initializer.with(ICONS.get(i));
      }
    }
  }

  public final Configurator withIcon(IconFontDescriptor descriptor) {
    ICONS.add(descriptor);
    return this;
  }

  public final Configurator withInterceptor(Interceptor interceptor) {
    INTERCEPTORS.add(interceptor);
    LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
    return this;
  }

  public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
    INTERCEPTORS.addAll(interceptors);
    LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
    return this;
  }

  private void checkConfiguration() {
    final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
    if (!isReady) {
      throw new RuntimeException("Configuration is not ready, call configure");
    }
  }

  @SuppressWarnings("unchecked") final <T> T getConfiguration(Object key) {
    checkConfiguration();
    final Object value = LATTE_CONFIGS.get(key);
    if (value == null) {
      throw new NullPointerException(key.toString() + " IS NULL");
    }
    return (T) LATTE_CONFIGS.get(key);
  }
}
