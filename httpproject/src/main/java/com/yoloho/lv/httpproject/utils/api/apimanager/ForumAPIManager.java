package com.yoloho.lv.httpproject.utils.api.apimanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yoloho.lv.httpproject.domain.forum.TopicDetailResult;
import com.yoloho.lv.httpproject.utils.api.ClientAPI;
import com.yoloho.lv.httpproject.utils.api.RetrofitAPIManager;
import com.yoloho.lv.httpproject.utils.api.netservices.forum.ITopicService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mylinux on 16/05/10.
 */
public class ForumAPIManager extends RetrofitAPIManager {
    private static class SingletonHolder {
        private static final ForumAPIManager INSTANCE = new ForumAPIManager();
    }

    private ForumAPIManager() {
    }

    public static final ForumAPIManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 1.converters 被添加的顺序将是它们被Retrofit尝试的顺序。如果我们希望传入一个自定义的Gson 解析实例，也是可以指定的
     */
    public Call<TopicDetailResult> queryTopicDetailById() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ClientAPI.getInstance().getTopicEndPoint())
                .client(genericClient())
                .build();
        ITopicService topicService = retrofit.create(ITopicService.class);
        Map<String, String> params = getPublicParams();//new HashMap<>();
        if (ClientAPI.isDebug) {
            params.put("id", "104973809");
        } else {
            params.put("id", "20767132");
        }
//        params.put("nowPage", "0");
//        params.put("refreshtime", "0");
        //getPublicParams(),
        Call<TopicDetailResult> call = topicService.loadTopicDetailInfo("topic", "detail", params);
        return call;
    }

    public Call<TopicDetailResult> test() {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://publicobject.com/helloworld.txt/")
                .client(genericClient())
                .build();
        ITopicService topicService = retrofit.create(ITopicService.class);
        Call<TopicDetailResult> call = topicService.loadNetTestData();
        return call;
    }
}
