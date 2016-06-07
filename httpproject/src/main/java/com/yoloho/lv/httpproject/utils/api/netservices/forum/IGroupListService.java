package com.yoloho.lv.httpproject.utils.api.netservices.forum;

import com.yoloho.lv.httpproject.domain.AD.Advert;
import com.yoloho.lv.httpproject.domain.HttpResult;
import com.yoloho.lv.httpproject.domain.forum.AttentionDataBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by mylinux on 16/06/05.
 */
public interface IGroupListService {
    @POST("/{namespace}/{method}")
    Call<AttentionDataBean> loadAttentionInfo(@Path("namespace") String namespace, @Path("method") String method, @QueryMap Map<String, String> dynamic, @QueryMap Map<String, String> params);


    @POST("/{namespace}/{method}")
    Call<HttpResult<Advert>> loadTopicDetailInfo(@Path("namespace") String namespace, @Path("method") String method, @QueryMap Map<String, String> dynamic);
}
