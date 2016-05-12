package com.yoloho.lv.httpproject.utils.api.apimanager;

import com.yoloho.lv.httpproject.domain.user.UserInfoModel;
import com.yoloho.lv.httpproject.utils.api.ClientAPI;
import com.yoloho.lv.httpproject.utils.api.RetrofitAPIManager;
import com.yoloho.lv.httpproject.utils.api.netservices.userinfo.IUserService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mylinux on 16/05/10.
 */
public class UserAPIManager extends RetrofitAPIManager{

    // 1.采用静态初始化器的方式,它可以由JVM来保证线程的安全性
    // 2.采用类级内部类,在此类里面创建对象,保证类在装载的时候不去初始化对象,达到延迟加载
    // 3.getInstance第一次调用的时候,读取SingletonHolder.INSTANCE,导致SingletonHolder类的初始化,而这个类在装载并初始化的时候,会初始化它的静态域
    private static class SingletonHolder {
        private static final UserAPIManager INSTANCE = new UserAPIManager();
    }

    private UserAPIManager() {
    }

    public static final UserAPIManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取到用户网络请求的句柄
     * 1.可以执行异步请求(请在后台线程执行)
     * 2.可以执行同步请求
     */
    public Call<UserInfoModel> getUserInfoRequestCall() {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ClientAPI.getInstance().getUICEndPoint()).client(genericClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        IUserService userInfoService = retrofit.create(IUserService.class);
        Call<UserInfoModel> call = userInfoService.loadUserInfo("user", "getinfo", getPublicParams());
        return call;
    }


}
