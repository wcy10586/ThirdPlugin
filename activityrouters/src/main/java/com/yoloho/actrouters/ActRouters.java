package com.yoloho.actrouters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lv
 */
public class ActRouters {
    private static final String TAG = ActRouters.class.getSimpleName();
    private static List<Mapping> mappings = new ArrayList<>();

    private static void initIfNeed() {
        long startTime = System.currentTimeMillis();
        if (!mappings.isEmpty()) {
            return;
        }
//        RouterMapping.map();
        try {
            Class<?> clazz = Class.forName("com.yoloho.actrouters.RouterMapping");
            clazz.getMethod("map").invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Routers.init() cost " + (System.currentTimeMillis() - startTime) + "ms");
    }

    static void map(String format, Class<? extends Activity> activity, ExtraTypes extraTypes) {
        mappings.add(new Mapping(format, activity, extraTypes));
    }

    static void sort() {
        // ensure that user/collection is rank top over user/:userId
        // so scheme://user/collection will match user/collection not user/:userId
        Collections.sort(mappings, new Comparator<Mapping>() {
            @Override
            public int compare(Mapping lhs, Mapping rhs) {
                return lhs.getFormat().compareTo(rhs.getFormat()) * -1;
            }
        });
    }

    public static boolean open(Context context, String url) {
        return open(context, Uri.parse(url));
    }

    public static boolean open(Context context, String url, RouterCallback callback) {
        return open(context, Uri.parse(url), callback);
    }

    public static boolean open(Context context, Uri uri) {
        return open(context, uri, null);
    }

    public static boolean open(Context context, Uri uri, RouterCallback callback) {
        boolean success = false;
        try {
            if (callback != null) {
                callback.beforeOpen(context, uri);
            }
            success = doOpen(context, uri);
            if (callback != null) {
                if (success) {
                    callback.afterOpen(context, uri);
                } else {
                    callback.notFound(context, uri);
                }
            }
        } catch (Throwable e) {
            if (callback != null) {
                callback.error(context, uri, e);
            }
        }
        return success;
    }

    private static boolean doOpen(Context context, Uri uri) {
        initIfNeed();
        Path path = Path.create(uri);
        for (Mapping mapping : mappings) {
            if (mapping.match(path)) {
                Intent intent = new Intent(context, mapping.getActivity());
                intent.putExtras(mapping.parseExtras(uri));
                intent.putExtra(KEY_RAW_URL, uri.toString());
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    /**
     * 带请求码的打开activity
     */
    public static boolean open(Context context, String url, int requestCode) {
        return open(context, Uri.parse(url), requestCode);
    }

    public static boolean open(Context context, String url, RouterCallback callback, int requestCode) {
        return open(context, Uri.parse(url), callback, requestCode);
    }

    public static boolean open(Context context, Uri uri, int requestCode) {
        return open(context, uri, null, requestCode);
    }

    public static boolean open(Context context, Uri uri, RouterCallback callback, int requestCode) {
        boolean success = false;
        try {
            if (callback != null) {
                callback.beforeOpen(context, uri);
            }
            success = doOpen(context, uri, requestCode);
            if (callback != null) {
                if (success) {
                    callback.afterOpen(context, uri);
                } else {
                    callback.notFound(context, uri);
                }
            }
        } catch (Throwable e) {
            if (callback != null) {
                callback.error(context, uri, e);
            }
        }
        return success;
    }

    private static boolean doOpen(Context context, Uri uri, int requestCode) {
        initIfNeed();
        Path path = Path.create(uri);
        for (Mapping mapping : mappings) {
            if (mapping.match(path)) {
                Intent intent = new Intent(context, mapping.getActivity());
                intent.putExtras(mapping.parseExtras(uri));
                intent.putExtra(KEY_RAW_URL, uri.toString());
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    ((Activity) context).startActivityForResult(intent, requestCode);
                }
                return true;
            }
        }
        return false;
    }

    public static String KEY_RAW_URL = "com.yoloho.lv.httpproject.actrouter.KeyRawUrl";
}
