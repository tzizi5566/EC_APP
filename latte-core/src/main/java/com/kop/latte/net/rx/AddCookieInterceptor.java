package com.kop.latte.net.rx;

import android.support.annotation.NonNull;
import com.kop.latte.util.storage.LattePreference;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/10/21 15:20
 */
public final class AddCookieInterceptor implements Interceptor {

  @Override public Response intercept(@NonNull Chain chain) throws IOException {
    final Request.Builder builder = chain.request().newBuilder();
    Observable
        .just(LattePreference.getCustomAppProfile("cookie"))
        .subscribe(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            //给原生API请求附带上WebView拦截下来的Cookie
            builder.addHeader("Cookie", s);
          }
        });
    return chain.proceed(builder.build());
  }
}
