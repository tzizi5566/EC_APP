package com.kop.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 19:48
 */
public class Configurator {

  private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();
  private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

  private Configurator() {
    LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
  }

  public static Configurator getInstance() {
    return Holder.INSTANCE;
  }

  final HashMap<String, Object> getLatteConfigs() {
    return LATTE_CONFIGS;
  }

  private static class Holder {
    private static final Configurator INSTANCE = new Configurator();
  }

  public final void configure() {
    initIcons();
    LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
  }

  public final Configurator withApiHost(String host) {
    LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
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

  private void checkConfiguration() {
    final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
    if (!isReady) {
      throw new RuntimeException("Configuration is not ready, call configure");
    }
  }

  final <T> T getConfiguration(Enum<ConfigType> key) {
    checkConfiguration();
    return (T) LATTE_CONFIGS.get(key.name());
  }
}
