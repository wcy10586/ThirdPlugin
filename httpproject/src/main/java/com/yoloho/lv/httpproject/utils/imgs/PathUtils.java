package com.yoloho.lv.httpproject.utils.imgs;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by mylinux on 16/09/22.
 */
public class PathUtils {
    /**
     * 获得根目录/data 内部存储路径
     */
    public static String getDataDirectory() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * 获得缓存目录/cache
     */
    public static String getDownloadCacheDirectory() {
        return Environment.getDownloadCacheDirectory().getPath();
    }

    /**
     * 获得SD卡目录/mnt/sdcard（获取的是手机外置sd卡的路径）
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获得系统目录/
     */
    public static String getRootDirectory() {
        return Environment.getRootDirectory().getPath();
    }


    public static File getCacheDir(Context mContext) {
        return mContext.getCacheDir();
    }

    public static String getCacheDirPath(Context mContext) {
        return mContext.getCacheDir().getPath();
    }

    /**
     * 用于获取APP的cache目录/data/data/<application package>/cache目录
     */
    public static String getExternalCacheDirPath(Context mContext) {
        return mContext.getExternalCacheDir().getPath();
    }

    /**
     * 用于获取APP的在SD卡中的cache目录/mnt/sdcard/Android/data/<application package>/cache
     */
    public static String getFilesDirPath(Context mContext) {
        return mContext.getFilesDir().getPath();
    }

    public static File getDiskCacheDir(Context mContext, String dirName) {
        String filepath = getExternalStorageDirectory();//getExternalCacheDirPath(mContext)
        File file = new File(filepath, dirName);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return file;
    }

    /**
     * 图片的缓存路径(为glide设置缓存目录使用)
     */
    public static File getImgDiskCacheDir(Context mContext) {
        String filepath = getExternalStorageDirectory();//getExternalCacheDirPath(mContext)
//        File result = new File(filepath+"/u_imgs_cache");
        File file = Glide.getPhotoCacheDir(mContext);
        if (null != file) {
            return file;
        }
        File cacheDir = mContext.getCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, "u_imgs_cache");
            if (!result.mkdirs() && (!result.exists() || !result.isDirectory())) {
                // File wasn't able to create a directory, or the result exists but not a directory
                return null;
            }
            return result;
        }
        return null;
    }
}
