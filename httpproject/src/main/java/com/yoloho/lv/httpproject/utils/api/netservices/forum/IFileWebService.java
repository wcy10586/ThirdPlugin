package com.yoloho.lv.httpproject.utils.api.netservices.forum;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 文件的上传和下载
 * <ul>
 * <li> HTTP协议之multipart/form-data请求分析
 * http://my.oschina.net/cnlw/blog/168466
 * </li>
 * </ul>
 * Created by mylinux on 16/06/13.
 */
public interface IFileWebService {
    //multipart/form-data
//    @Headers({"Content-Type: image/jpeg"})
    @GET
    @Streaming
    Call dowloadFile(@Url String url);

    /**
     * 上传一张图片
     *
     * @param description
     * @param imgs
     * @return
     */
    @Multipart
    @POST("topic/addtopic")
    Call<String> uploadImage(@Part("fileName") String description, @Part("file\"; filename=\"image.png\"") RequestBody imgs);


    /**
     * 上传6张图片
     *
     * @param description
     * @param imgs1
     * @param imgs2
     * @param imgs3
     * @param imgs4
     * @param imgs5
     * @param imgs6
     * @return
     */
    @Multipart
    @POST("topic/addtopic")
    Call<String> uploadImage(@Part("description") String description,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs1,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs2,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs3,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs4,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs5,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs6);

    /**
     * 简便写法
     *
     * @param description
     * @param imgs1
     * @return
     */
    @Multipart
    @POST("topic/addtopic")
    Call<String> uploadImage(@Part("description") String description, @PartMap Map<String, RequestBody> imgs1);

}
