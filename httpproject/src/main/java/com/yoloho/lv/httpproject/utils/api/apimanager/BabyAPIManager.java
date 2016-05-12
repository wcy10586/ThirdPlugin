package com.yoloho.lv.httpproject.utils.api.apimanager;

import com.yoloho.lv.httpproject.utils.api.ClientAPI;
import com.yoloho.lv.httpproject.utils.api.RetrofitAPIManager;
import com.yoloho.lv.httpproject.utils.api.netservices.baby.IBabyInfoService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mylinux on 16/05/10.
 */
public class BabyAPIManager extends RetrofitAPIManager {
    // 1.采用静态初始化器的方式,它可以由JVM来保证线程的安全性
    // 2.采用类级内部类,在此类里面创建对象,保证类在装载的时候不去初始化对象,达到延迟加载
    // 3.getInstance第一次调用的时候,读取SingletonHolder.INSTANCE,导致SingletonHolder类的初始化,而这个类在装载并初始化的时候,会初始化它的静态域
    private static class SingletonHolder {
        private static final BabyAPIManager INSTANCE = new BabyAPIManager();
    }

    private BabyAPIManager() {
    }

    public static final BabyAPIManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取宝宝基本信息的网络请求
     * 1.得到的请求的句柄
     * 2.此请求是执行网络请求
     */
    public Call getBabyInfoRequestCall() {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ClientAPI.getInstance().getBABYCALEndPoint())
                .client(genericClient())
                .build();
        IBabyInfoService userInfoService = retrofit.create(IBabyInfoService.class);
        Call call = userInfoService.loadBaseInfo("babyInfo", "getBabyInfo", getPublicParams());
        return call;
    }

    /**
     * 获取宝宝基本信息的网络请求
     * 1.得到的请求的句柄
     * 2.此请求是执行网络请求,但进行了缓存机制的处理
     */
    public Call getBabyInfoRequestCallByCache() {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ClientAPI.getInstance().getBABYCALEndPoint())
                .client(genericClient())
                .build();
        IBabyInfoService userInfoService = retrofit.create(IBabyInfoService.class);
        Call call = userInfoService.loadBaseInfoCache("babyInfo", "getBabyInfo", getPublicParams());
        return call;
    }
}
