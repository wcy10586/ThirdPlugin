package com.yoloho.lv.httpproject.utils.api;

import android.os.Environment;

import com.yoloho.lv.httpproject.utils.tools.NetUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 1.okhttp网络底层的工具封装处理逻辑
 * <p/>
 * <ul>
 * <li>1.整个app全局最好只初始化一个OkHttpClient实例</li>
 * <li>2.需要考虑到并发,在单例初始化时候创建了一次,在之后使用中调用getOkHttpClient方法(此方法没有支持并发,可以优化),基本不会创建新的实例了</li>
 * </ul>
 * 参考链接 http://www.jianshu.com/p/9cebbbd0eeab
 * Created by mylinux on 16/05/10.
 */
public class OkHttpUtils {
    private static class SingletonHolder {
        private static final OkHttpUtils INSTANCE = new OkHttpUtils();
    }

    private OkHttpUtils() {
        getOkHttpClient();
    }

    public static final OkHttpUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final String TAG = "okhttp";
    private static final String STORAGE_DIRECTORY = "yoloho/ubaby/important/files/ing/cache";
    /**
     * OkHttp官方文档并不建议我们创建多个OkHttpClient，因此全局使用一个。 如果有需要，可以使用clone方法，再进行自定义
     * 1.okhttp提供的日志系统，HttpLoggingInterceptor.
     * 2.addHeader("Connection", "Keep-Alive") 是不需要写了，因为 HttpEngine 的 networkRequest 方法里已经帮我们加上去了
     */
    private final static OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();

    private static OkHttpClient mOkHttpClient = null;

    public OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            OkHttpClient client = mOkHttpClientBuilder.cache(getHttpCache())
//                    .addNetworkInterceptor((Interceptor) new StethoInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor())
                    .addInterceptor(new UserAgentInterceptor("OkHttp Headers.java"))
                    .build();
            mOkHttpClient = client;
        }
        return mOkHttpClient;
    }

    /**
     * 设置缓存的位置
     */
    private Cache getHttpCache() {
        File cacheFile = new File(Environment.getExternalStorageDirectory(), STORAGE_DIRECTORY);//缓存目录
        return (new Cache(cacheFile, 1024 * 1024 * 100)); //100Mb
    }


    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkOnline()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isNetworkOnline()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR2 = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "max-age=60")
                    .build();
        }
    };
}
