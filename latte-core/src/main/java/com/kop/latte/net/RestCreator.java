package com.kop.latte.net;

import com.kop.latte.app.ConfigKeys;
import com.kop.latte.app.Latte;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 20:31
 */
public class RestCreator {

  private static final class ParamsHolder {
    public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
  }

  public static WeakHashMap<String, Object> getParams() {
    return ParamsHolder.PARAMS;
  }

  public static RestService getRestService() {
    return RestServiceHolder.REST_SERVICE;
  }

  private static final class RetrofitHolder {

    private static final String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);

    private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OKHttpHolder.OK_HTTP_CLIENT)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();
  }

  private static final class OKHttpHolder {

    private static final int TIME_OUT = 60;
    private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
    private static final ArrayList<Interceptor> INTERCEPTORS =
        Latte.getConfiguration(ConfigKeys.INTERCEPTOR);

    private static OkHttpClient.Builder addInterceptor() {
      if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
        for (Interceptor interceptor : INTERCEPTORS) {
          BUILDER.addInterceptor(interceptor);
        }
      }
      return BUILDER;
    }

    private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build();
  }

  private static final class RestServiceHolder {

    private static final RestService REST_SERVICE =
        RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
  }
}
