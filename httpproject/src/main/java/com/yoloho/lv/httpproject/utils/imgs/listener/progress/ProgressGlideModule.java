package com.yoloho.lv.httpproject.utils.imgs.listener.progress;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by mylinux on 16/06/01.
 */
public class ProgressGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(String.class, InputStream.class, new ProgressModelLoader.Factory());
    }
}
