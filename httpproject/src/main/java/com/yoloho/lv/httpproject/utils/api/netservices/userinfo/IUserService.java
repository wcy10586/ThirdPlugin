package com.yoloho.lv.httpproject.utils.api.netservices.userinfo;

import com.yoloho.lv.httpproject.domain.user.UserInfoModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by mylinux on 16/03/24.
 */
public interface IUserService {
    @POST("/{namespace}/{method}")
    Call<UserInfoModel> loadUserInfo(@Path("namespace") String namespace, @Path("method") String method, @QueryMap Map<String, String> dynamic);
}
