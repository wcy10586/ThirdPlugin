package com.yoloho.lv.httpproject.domain;

/**
 * 相同格式的Http请求数据的结果处理
 * 1.采用范型
 * 2如果data是一个User对象的话。那么在定义Service方法的返回值就可以写为Call<HttpResult<User>>
 * Created by mylinux on 16/06/05.
 */
public class HttpResult<T> {
    public String timestamp;
    public String errno;
    public String errdesc;
    public T data;
}
