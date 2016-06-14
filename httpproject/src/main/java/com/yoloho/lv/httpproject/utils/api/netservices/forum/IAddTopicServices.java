package com.yoloho.lv.httpproject.utils.api.netservices.forum;

import com.yoloho.lv.httpproject.domain.user.UserInfoModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by mylinux on 16/06/13.
 */
public interface IAddTopicServices {
    @Multipart
    @POST("topic/addtopic")
    Call<UserInfoModel> addNewTopic(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);

}
