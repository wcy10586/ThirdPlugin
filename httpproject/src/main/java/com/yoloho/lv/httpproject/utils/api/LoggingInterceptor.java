package com.yoloho.lv.httpproject.utils.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求的日志拦截器,主要是打印log排查问题
 * <p/>
 * 参考:https://github.com/square/okhttp/wiki/Interceptors
 * Created by mylinux on 16/05/11.
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.e("httpRequestLog", (String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers())));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.e("httpRequestLog", (String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers())));
        return response;
    }
}