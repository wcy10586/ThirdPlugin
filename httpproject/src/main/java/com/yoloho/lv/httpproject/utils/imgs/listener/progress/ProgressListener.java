package com.yoloho.lv.httpproject.utils.imgs.listener.progress;

/**
 * Created by mylinux on 16/06/01.
 */
public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
