package com.yoloho.lv.httpproject.utils.api.callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

/**
 * 网络请求数据的回调
 * Created by mylinux on 16/06/15.
 */
public interface HttpResultCallBack {
    void onSuccess(JSONObject json) throws JSONException, SecurityException, IllegalArgumentException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException;

    ;

    void onError(JSONObject json);
}
