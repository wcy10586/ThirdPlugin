package com.yoloho.lv.httpproject.utils.api.apimanager;

import android.util.Log;

import com.yoloho.lv.httpproject.utils.api.ClientAPI;
import com.yoloho.lv.httpproject.utils.api.RetrofitAPIManager;
import com.yoloho.lv.httpproject.utils.api.netservices.forum.IFileWebService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 负载文件上传和下载
 * 1发表说说,包含图片
 * Created by mylinux on 16/06/14.
 */
public class FileUpLoadAPIManager extends RetrofitAPIManager {
    private static class SingletonHolder {
        private static final FileUpLoadAPIManager INSTANCE = new FileUpLoadAPIManager();
    }

    private FileUpLoadAPIManager() {
    }

    public static final FileUpLoadAPIManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 发说说,最多可以发表6张图片
     *
     * @param paths
     * @param desp
     */
    public void upload(ArrayList<String> paths, String desp) {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ClientAPI.getInstance().getTopicEndPoint())
                .client(genericClient())
                .build();

        RequestBody[] requestBody = new RequestBody[6];
        if (paths.size() > 0) {
            for (int i = 0; i < paths.size(); i++) {
                requestBody[i] = RequestBody.create(MediaType.parse("multipart/form-data"), new File(paths.get(i)));
            }
        }
        final IFileWebService apiManager = retrofit.create(IFileWebService.class);
        Call<String> call = apiManager.uploadImage(desp, requestBody[0], requestBody[1], requestBody[2], requestBody[3], requestBody[4], requestBody[5]);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("tag_img", "onResponse() called with: " + "call = [" + call + "], response = [" + response + "]");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("tag_img", "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
            }
        });

    }

    /**
     * @param paths
     * @param desp
     */
    public void uploadMany(ArrayList<String> paths, String desp) {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ClientAPI.getInstance().getTopicEndPoint())
                .client(genericClient())
                .build();

        Map<String, RequestBody> photos = new HashMap<>();
        if (paths.size() > 0) {
            for (int i = 0; i < paths.size(); i++) {
                photos.put("photos\"; filename=\"icon.png", RequestBody.create(MediaType.parse("multipart/form-data"), new File(paths.get(i))));
            }
        }
        final IFileWebService apiManager = retrofit.create(IFileWebService.class);

        Call<String> stringCall = apiManager.uploadImage(desp, photos);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("tag_img", "onResponse() called with: " + "call = [" + call + "], response = [" + response + "]");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("tag_img", "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}
