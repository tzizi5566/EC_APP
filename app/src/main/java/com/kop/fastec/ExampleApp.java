package com.kop.fastec;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import cn.jpush.android.api.JPushInterface;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.kop.fastec.event.ShareEvent;
import com.kop.fastec.event.TestEvent;
import com.kop.latte.app.Latte;
import com.kop.latte.ec.database.DatabaseManager;
import com.kop.latte.ec.icon.FontEcModule;
import com.kop.latte.net.rx.AddCookieInterceptor;
import com.kop.latte.util.callback.CallbackManager;
import com.kop.latte.util.callback.CallbackType;
import com.kop.latte.util.callback.IGlobalCallback;
import com.mob.MobSDK;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/7 19:45
 */
public class ExampleApp extends MultiDexApplication {

  @Override public void onCreate() {
    super.onCreate();
    Latte.init(this)
        .withIcon(new FontAwesomeModule())
        .withIcon(new FontEcModule())
        .withApiHost("http://7xslu7.com1.z0.glb.clouddn.com/")
        .withWebHost("https://www.baidu.com/")
        .withInterceptor(new AddCookieInterceptor())//添加cookie同步拦截器
        .withJavascriptInterface("latte")
        .withWebEvent("test", new TestEvent())
        .withWebEvent("share", new ShareEvent())
        .configure();

    //GreenDao
    DatabaseManager.getInstance().init(this);

    //Utils
    Utils.init(this);

    //极光推送
    JPushInterface.setDebugMode(true);
    JPushInterface.init(this);

    CallbackManager.getInstance()
        .addCallback(CallbackType.OPEN_PUSH, new IGlobalCallback() {
          @Override public void executeCallback(@Nullable Object args) {
            if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
              JPushInterface.setDebugMode(true);
              JPushInterface.init(Latte.getApplicationContext());
            }
          }
        })
        .addCallback(CallbackType.STOP_PUSH, new IGlobalCallback() {
          @Override public void executeCallback(@Nullable Object args) {
            if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
              JPushInterface.stopPush(Latte.getApplicationContext());
            }
          }
        });

    MobSDK.init(this, "2263b22df5b69", "5a69709a2a789aa591d37c8576537f6e");

    initStetho();

    Fragmentation.builder()
        // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
        .stackViewMode(Fragmentation.BUBBLE)
        // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
        // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
        .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
        // 生产环境时，捕获上述异常（避免crash），会捕获
        // 建议在回调处上传下面异常到崩溃监控服务器
        .handleException(new ExceptionHandler() {
          @Override
          public void onException(Exception e) {
            // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
            // Bugtags.sendException(e);
          }
        })
        .install();
  }

  private void initStetho() {
    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
  }

  @Override
  protected void attachBaseContext(Context context) {
    super.attachBaseContext(context);
    MultiDex.install(this);
  }
}
