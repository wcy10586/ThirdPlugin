package com.yoloho.lv.httpproject.utils.api.netservices.forum;

import com.yoloho.lv.httpproject.domain.forum.TopicDetailResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by mylinux on 16/05/11.
 */
public interface ITopicService {
    //, @QueryMap Map<String, String> params
    @POST("/{namespace}/{method}")
    Call<TopicDetailResult> loadTopicDetailInfo(@Path("namespace") String namespace, @Path("method") String method, @QueryMap Map<String, String> dynamic);

    @GET("https://publicobject.com/helloworld.txt")
    Call<TopicDetailResult> loadNetTestData();
}
