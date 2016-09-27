package com.yoloho.actrouters;

import android.content.Context;
import android.net.Uri;

/**
 * Created by lv
 */
public interface RouterCallback {
    void notFound(Context context, Uri uri);

    void beforeOpen(Context context, Uri uri);

    void afterOpen(Context context, Uri uri);
    void error(Context context, Uri uri, Throwable e);
}
