package com.yoloho.lv.httpproject.utils.api.netservices.forum;

import retrofit2.Call;
import retrofit2.http.GET;
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
}
