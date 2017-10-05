package com.kop.fastec;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.kop.latte.app.Latte;
import com.kop.latte.ec.database.DatabaseManager;
import com.kop.latte.ec.icon.FontEcModule;
import com.kop.latte.net.interceptors.DebugInterceptor;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 19:45
 */
public class ExampleApp extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Latte.init(this)
        .withIcon(new FontAwesomeModule())
        .withIcon(new FontEcModule())
        .withApiHost("http://7xslu7.com1.z0.glb.clouddn.com/")
        .withInterceptor(new DebugInterceptor("index", R.raw.test))
        .configure();
    initStetho();
    DatabaseManager.getInstance().init(this);
  }

  private void initStetho() {
    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
  }
}