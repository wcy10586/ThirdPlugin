package com.yoloho.actrouters;

import android.content.Context;
import android.net.Uri;

/**
 * Created by lv
 */
public class SimpleRouterCallback implements RouterCallback {
    @Override
    public void notFound(Context context, Uri uri) {
    }

    @Override
    public void beforeOpen(Context context, Uri uri) {
    }

    @Override
    public void afterOpen(Context context, Uri uri) {
    }

    @Override
    public void error(Context context, Uri uri, Throwable e) {

    }
}
