package com.yoloho.actrouters;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by lv
 */
public class RouterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RouterCallback callback = getRouterCallback();

        Uri uri = getIntent().getData();
        if (uri != null) {
            if (callback == null) {
                ActRouters.open(this, uri);
            } else {
                callback.beforeOpen(this, uri);
                boolean success = ActRouters.open(this, uri);
                if (success) {
                    callback.afterOpen(this, uri);
                } else {
                    callback.notFound(this, uri);
                }
            }
        }
        finish();
    }

    private RouterCallback getRouterCallback() {
        if (getApplication() instanceof RouterCallbackProvider) {
            return ((RouterCallbackProvider) getApplication()).provideRouterCallback();
        }
        return null;
    }
}
