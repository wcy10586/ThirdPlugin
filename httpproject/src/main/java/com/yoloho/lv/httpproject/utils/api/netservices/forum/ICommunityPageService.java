package com.yoloho.lv.httpproject.utils.api.netservices.forum;

import com.yoloho.lv.httpproject.domain.forum.AttentionDataBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 交流--我的关注人接口获取网络数据
 * Created by mylinux on 16/05/19.
 */
public interface ICommunityPageService {
    @POST("/{namespace}/{method}")
    Call<AttentionDataBean> loadAttentionInfo(@Path("namespace") String namespace, @Path("method") String method, @QueryMap Map<String, String> dynamic, @QueryMap Map<String, String> params);

}
