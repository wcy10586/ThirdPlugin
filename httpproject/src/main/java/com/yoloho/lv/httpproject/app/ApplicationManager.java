package com.yoloho.lv.httpproject.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.SparseArray;

import java.lang.ref.WeakReference;

/**
 * Created by mylinux on 16/03/23.
 */
public class ApplicationManager extends Application {
    protected static Context instance;

    @Override
    public void onCreate() {
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.onCreate();
        instance = getApplicationContext();
    }

    // 实例对象
    public static WeakReference<Activity> instanceRef;

    public static synchronized Context getInstance() {
        if (instanceRef == null || instanceRef.get() == null) {
            return ApplicationManager.getContext();
        } else {
            return instanceRef.get();
        }
    }

    public static synchronized Activity getActivity() {
        Activity result = null;
        if (instanceRef != null && instanceRef.get() != null) {
            result = instanceRef.get();
        }
        return result;
    }

    public static synchronized Context getContext() {
        return instance;
    }

    public static SparseArray<WeakReference<Activity>> taskStack = new SparseArray<WeakReference<Activity>>();

    public static synchronized SparseArray<WeakReference<Activity>> getTaskStack() {
        return taskStack;
    }
}
