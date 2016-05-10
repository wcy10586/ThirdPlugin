package com.yoloho.lv.httpproject.utils.api;

import com.yoloho.lv.httpproject.domain.user.UserInfoModel;
import com.yoloho.lv.httpproject.utils.api.netservices.baby.IBabyInfoService;
import com.yoloho.lv.httpproject.utils.api.netservices.userinfo.IUserService;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mylinux on 16/03/23.
 * <p/>
 * 参考:http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0915/3460.html
 * Retrofit源码: http://www.boyunjian.com/javasrc/com.squareup.retrofit/retrofit/1.6.0/_/retrofit/http/QueryMap.java
 * https://github.com/square/retrofit
 * 如何用 Retrofit 2 在安卓上实现 HTTP 访问
 * http://news.oneapm.com/retrofit-url/
 * 入门讲解
 * http://www.th7.cn/Program/Android/201510/603107.shtml
 * http://www.tuicool.com/articles/fQju2uQ
 * http://www.jianshu.com/p/c1a3a881a144
 *
 * 扩展
 * http://www.cnblogs.com/peiandsky/p/4394779.html
 * http://www.jianshu.com/p/aad5aacd79bf/comments/1183989
 */
public class RetrofitAPIManager {
    // 1.采用静态初始化器的方式,它可以由JVM来保证线程的安全性
    // 2.采用类级内部类,在此类里面创建对象,保证类在装载的时候不去初始化对象,达到延迟加载
    // 3.getInstance第一次调用的时候,读取SingletonHolder.INSTANCE,导致SingletonHolder类的初始化,而这个类在装载并初始化的时候,会初始化它的静态域
    private static class SingletonHolder {
        private static final RetrofitAPIManager INSTANCE = new RetrofitAPIManager();
    }

    private RetrofitAPIManager() {
    }

    public static final RetrofitAPIManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    ////以下是测试
    public static final String SERVER_URL = "url";

//    public static ClientAPI provideClientApi() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(SERVER_URL)
//                .client(genericClient())
//                .build();
//        return retrofit.create(ClientAPI.class);
//    }


    public static OkHttpClient genericClient() {
        //okhttp提供的日志系统，HttpLoggingInterceptor.
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    //功能是可以拦截http请求进行监控，重写或重试
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Set-Cookie", "android framework request")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
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
        Call<UserInfoModel> call = userInfoService.loadUserInfo("user", "getinfo", ClientAPI.getInstance().setPostData());
        return call;
    }

    public Call getBabyInfoRequestCall() {
        //addConverterFactory(GsonConverterFactory.create())
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ClientAPI.getInstance().getBABYCALEndPoint())
                .client(genericClient())
                .build();
        IBabyInfoService userInfoService = retrofit.create(IBabyInfoService.class);
        Map<String, String> foo = ClientAPI.getInstance().setPostData();
        Call call = userInfoService.loadBaseInfo("babyInfo", "getBabyInfo", foo);
        return call;
    }
}
