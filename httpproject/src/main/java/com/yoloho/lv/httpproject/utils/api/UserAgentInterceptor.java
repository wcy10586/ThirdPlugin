package com.yoloho.lv.httpproject.utils.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建一个自定义个拦截器来取代OkHttp包含的User-Agent请求头
 * <ul>
 * Android系统的os值，用它来明确额标识出这是来自Android设备的请求 Build.MODEL,
 * 或“终端用户可见的产品名称” Build.BRAND, 或“消费者可见的产品或者硬件品牌名” Build.VERSION.SDK_INT,
 * 或“用户可见的AndroidSDK版本号” BuildConfig.APPLICATION_ID BuildConfig.VERSION_NAME BuildConfig.VERSION_CODE
 * </ul>
 * Created by mylinux on 16/06/15.
 */
public class UserAgentInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private final String userAgentHeaderValue ;

    public UserAgentInterceptor(String userAgentHeaderValue) {
        this.userAgentHeaderValue =userAgentHeaderValue;// Preconditions.checkNotNull(userAgentHeaderValue);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // //功能是可以拦截http请求进行监控，重写或重试
        final Request originalRequest = chain.request();
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
//                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept", "*/*")
                .addHeader("Set-Cookie", "android framework request")
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}
