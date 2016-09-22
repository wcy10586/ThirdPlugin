package com.yoloho.lv.httpproject.utils.imgs.listener;

import android.graphics.Bitmap;

/**
 * bitmap下载监听器
 * Created by mylinux on 16/09/22.
 */
public interface BitmapLoadingListener {
    void onSuccess(Bitmap b);

    void onError();
}
