package com.yoloho.lv.httpproject.utils.api.netservices.baby;

import com.yoloho.lv.httpproject.domain.baby.BabyInfoModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by mylinux on 16/03/24.
 */
public interface IBabyInfoService {
    @GET("/{namespace}/{method}")
    Call<BabyInfoModel> loadBaseInfo(@Path("namespace") String namespace, @Path("method") String method, @QueryMap(encoded = true) Map<String, String> dynamic);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("/{namespace}/{method}")
    Call<BabyInfoModel> loadBaseInfoCache(@Path("namespace") String namespace, @Path("method") String method, @QueryMap(encoded = true) Map<String, String> dynamic);

}
