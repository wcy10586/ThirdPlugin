package com.yoloho.lv.httpproject.utils.api.apimanager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yoloho.lv.httpproject.domain.HttpResult;
import com.yoloho.lv.httpproject.domain.deserializer.AttentionDataDeserializer;
import com.yoloho.lv.httpproject.domain.deserializer.AttentionDeserializer;
import com.yoloho.lv.httpproject.domain.deserializer.TopicADdeserializer;
import com.yoloho.lv.httpproject.domain.forum.AttentionDataBean;
import com.yoloho.lv.httpproject.domain.forum.AttentionInfoBean;
import com.yoloho.lv.httpproject.domain.forum.TopicDetailResult;
import com.yoloho.lv.httpproject.utils.api.ClientAPI;
import com.yoloho.lv.httpproject.utils.api.RetrofitAPIManager;
import com.yoloho.lv.httpproject.utils.api.callback.HttpResultCallBack;
import com.yoloho.lv.httpproject.utils.api.netservices.forum.ICommunityPageService;
import com.yoloho.lv.httpproject.utils.api.netservices.forum.IGroupListService;
import com.yoloho.lv.httpproject.utils.api.netservices.forum.ITopicService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 论坛相关的网络获取管理者
 * 1.采用的是单例模式
 * <ul>
 * <li>
 * 1.通过话题id查询话题详情,返回的是查询的句柄,不可以重复使用,可以先克隆再使用
 * </li>
 * <li>
 * 2.获取关注人列表的数据,这里进行了反序列化处理,自动解析请求回来的数据.
 * </li>
 * <li>
 * 3.小组话题列表数据,这里使用ScalarsConverterFactory转换器,目的是返回string数据,留给使用者按照原来习惯进行处理
 * </li>
 * <li>
 * 1.
 * </li>
 * <li>
 * 1.
 * </li>
 * <li>
 * 1.
 * </li>
 * <li>
 * 1.
 * </li>
 * <li>
 * 1.
 * </li>
 * <li>
 * 1.
 * </li>
 * <p/>
 * </ul>
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

    public Call<AttentionDataBean> getAttentionData(Map<String, String> params) {
        //we are creating an instance of Gson through the GsonBuilder. Using the registerTypeAdapter() method,
        // we are registering our deserializer and instructing Gson to use our deserializer when deserializing objects of type 'AttentionDataBean'.
        // When we request Gson to deserialize an object to the AttentionDataBean class, Gson will use our deserializer.
        // The following steps describes what happens when we invoke: gson.fromJson(data, AttentionDataBean.class).
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AttentionInfoBean.class, new AttentionDeserializer());
        gsonBuilder.registerTypeAdapter(AttentionDataBean.class, new AttentionDataDeserializer());
        final Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ClientAPI.getInstance().getTopicEndPoint())
                .client(genericClient())
                .build();
        ICommunityPageService attentionService = retrofit.create(ICommunityPageService.class);
        Call<AttentionDataBean> call = attentionService.loadAttentionInfo("topic", "followTopic", getPublicParams(), params);
        return call;
    }

    //小组下话题列表的
    private static Retrofit groupTopicListServiceRetrofit;

    private Retrofit getGroupTopicListServiceRetrofit() {
        if (null == groupTopicListServiceRetrofit) {
            groupTopicListServiceRetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(ClientAPI.getInstance().getTopicEndPoint())
                    .client(genericClient())
                    .build();
        }
        return groupTopicListServiceRetrofit;
    }

    public void getGroupListData(final HttpResultCallBack callback, final Map<String, String> params) {
        final Retrofit retrofit = getGroupTopicListServiceRetrofit();
        IGroupListService groupListService = retrofit.create(IGroupListService.class);
        Call<String> topicListCall = groupListService.loadGroupTopicList("topic", "groupTopic", getPublicParams(), params);
        topicListCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != callback) {
                    String result = response.body();
                    try {
                        if (TextUtils.isEmpty(result)) {
                            callback.onSuccess(new JSONObject());
                        } else {
                            callback.onSuccess(new JSONObject(result));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != callback) {
                    callback.onError(null);
                }
            }
        });
    }

    public void getForumAD(final HttpResultCallBack callback, final Map<String, String> params) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HttpResult.class, new TopicADdeserializer());
        final Gson gson = gsonBuilder.create();
    }
}
