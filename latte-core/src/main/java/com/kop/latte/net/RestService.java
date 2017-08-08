package com.kop.latte.net;

import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 功    能: //TODO
 * 创 建 人: KOP
 * 创建日期: 2017/8/8 17:45
 */
public interface RestService {

  @GET
  Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

  @POST
  @FormUrlEncoded
  Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

  @PUT
  @FormUrlEncoded
  Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

  @DELETE
  Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

  @GET
  @Streaming
  Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

  @POST
  @Multipart
  Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}
