package com.yoloho.lv.httpproject.utils.imgs;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.yoloho.lv.httpproject.utils.imgs.listener.BitmapLoadingListener;
import com.yoloho.lv.httpproject.utils.imgs.listener.ImgLoadListener;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 图片加载的工具类
 * <p>
 * <ul>
 * 图片加载使用
 * <li>
 * 1. GlideUtils.clearTarget(this, url);// 删除已加载过url的图片
 * 2. GlideUtils.loadStringRes(mTargetView, url, GlideLoadConfig.defConfig, mListener);//加载网络图片
 * 3.//加载网络的gif
 * GlideLoadConfig config2 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).setAsGif(true).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).
 * build();
 * GlideUtils.loadGif(mTargetView, gif, config2, mListener);
 * 4.//加载本地图片
 * GlideLoadConfig config3 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).setSize(size).
 * build();
 * GlideUtils.loadFile(mTargetView, jpgFile, config3, mListener);
 * 5.//加载本地资源
 * GlideLoadConfig config4 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE)
 * .build();
 * GlideUtils.loadResId(mTargetView, R.drawable.dog, config4, mListener);
 * 6.//加载资源动画
 * GlideLoadConfig config5 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).
 * setAnimResId(R.anim.left_in).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).
 * setAsGif(true).build();
 * GlideUtils.loadResId(mTargetView, R.drawable.smail, config5, mListener);
 * 7.//加载属性动画
 * ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
 *
 * @Override public void animate(View view) {
 * ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", -500f, 0f);
 * ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
 * ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
 * ObjectAnimator moveTop = ObjectAnimator.ofFloat(view, "translationY", 0f, -2000, 0f);
 * AnimatorSet animSet = new AnimatorSet();
 * animSet.play(rotate).with(fadeInOut).after(moveIn).before(moveTop);
 * animSet.setDuration(5000);
 * animSet.start();
 * }
 * };
 * GlideLoadConfig config6 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).
 * setAnimator(animationObject).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).
 * setAsGif(true).
 * build();
 * GlideUtils.loadResId(mTargetView, R.drawable.smail, config6, mListener);
 * 8.//先加载缩略图片,再显示原图
 * GlideLoadConfig config7 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).
 * setThumbnailUrl(thumbnailUrl)
 * .build();
 * GlideUtils.loadStringRes(mTargetView, url, config7, mListener);
 * 9.GlideLoadConfig config8 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).
 * setSkipMemoryCache(true).
 * setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).
 * setThumbnail(0.7f)
 * .build();
 * GlideUtils.loadStringRes(mTargetView, url, config8, mListener);
 * </li>
 * 资源解释说明
 * <li>SD卡资源："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"<p/>
 * assets资源："file:///android_asset/f003.gif"<p/>
 * raw资源："Android.resource://com.frank.glide/raw/raw_1"或"android.resource://com.frank.glide/raw/"+R.raw.raw_1<p/>
 * drawable资源："android.resource://com.frank.glide/drawable/news"或load"android.resource://com.frank.glide/drawable/"+R.drawable.news<p/>
 * ContentProvider资源："content://media/external/images/media/139469"<p/>
 * http资源："http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg"<p/>
 * https资源："https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp"<p/></li>
 * 参考文献
 * <li>http://blog.csdn.net/shangmingchao/article/details/51125554
 * Glide使用详解（一）
 * </li>
 * </ul>
 * Created by mylinux on 16/06/01.
 */
public class GlideUtils {

    /**
     * 加载String类型的资源
     *
     * @param view
     * @param imageUrl
     * @param config
     * @param listener
     */
    public static void loadStringRes(ImageView view, String imageUrl, GlideLoadConfig config, ImgLoadListener listener) {
        load(view.getContext(), view, imageUrl, config, listener);
    }

    public static void loadFile(ImageView view, File file, GlideLoadConfig config, ImgLoadListener listener) {
        load(view.getContext(), view, file, config, listener);
    }

    public static void loadResId(ImageView view, Integer resourceId, GlideLoadConfig config, ImgLoadListener listener) {
        load(view.getContext(), view, resourceId, config, listener);
    }

    public static void loadUri(ImageView view, Uri uri, GlideLoadConfig config, ImgLoadListener listener) {
        load(view.getContext(), view, uri, config, listener);
    }

    public static void loadGif(ImageView view, String gifUrl, GlideLoadConfig config, ImgLoadListener listener) {
        load(view.getContext(), view, gifUrl, GlideLoadConfig.parseBuilder(config).setAsGif(true).build(), listener);
    }

    public static void loadTarget(Context context, Object objUrl, GlideLoadConfig config, final ImgLoadListener listener) {
        load(context, null, objUrl, config, listener);
    }

    /**
     * 使用signature()实现自定义cacheKey：
     * Glide 以 url、viewwidth、viewheight、屏幕的分辨率等做为联合key，官方api中没有提供删除图片缓存的函数，官方提供了signature()方法，
     * 将一个附加的数据加入到缓存key当中，多媒体存储数据，
     * 可用MediaStoreSignature类作为标识符，会将文件的修改时间、mimeType等信息作为cacheKey的一部分，通过改变key来实现图片失效相当于软删除。
     */
    private static void load(Context context, ImageView view, Object objUrl, GlideLoadConfig config, final ImgLoadListener listener) {
        if (null == objUrl) {
            throw new IllegalArgumentException("objUrl is null");
        } else if (null == config) {
            config = defConfig;
        }
        try {
            GenericRequestBuilder builder = null;
            if (config.isAsGif()) {//gif类型
                GifRequestBuilder request = Glide.with(context).load(objUrl).asGif();
                if (config.getCropType() == GlideLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (config.isAsBitmap()) {  //bitmap 类型
                BitmapRequestBuilder request = Glide.with(context).load(objUrl).asBitmap();
                if (config.getCropType() == GlideLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                //transform bitmap
                if (config.isRoundedCorners()) {
                    request.transform(new RoundedCornersTransformation(context, 50, 50));
                } else if (config.isCropCircle()) {
                    request.transform(new CropCircleTransformation(context));
                } else if (config.isGrayscale()) {
                    request.transform(new GrayscaleTransformation(context));
                } else if (config.isBlur()) {
                    request.transform(new BlurTransformation(context, 8, 8));
                } else if (config.isRotate()) {
                    request.transform(new RotateTransformation(context, config.getRotateDegree()));
                }
                builder = request;
            } else if (config.isCrossFade()) { // 渐入渐出动画
                DrawableRequestBuilder request = Glide.with(context).load(objUrl).crossFade();
                if (config.getCropType() == GlideLoadConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            }
            //缓存设置
            builder.diskCacheStrategy(config.getDiskCacheStrategy().getStrategy()).
                    skipMemoryCache(config.isSkipMemoryCache()).
                    priority(config.getPrioriy().getPriority());
            builder.dontAnimate();
            if (null != config.getTag()) {
                builder.signature(new StringSignature(config.getTag()));
            } else {
                builder.signature(new StringSignature(objUrl.toString()));
            }
            if (null != config.getAnimator()) {
                builder.animate(config.getAnimator());
            } else if (null != config.getAnimResId()) {
                builder.animate(config.getAnimResId());
            }
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }
            if (null != config.getErrorResId()) {
                builder.error(config.getErrorResId());
            }
            if (null != config.getPlaceHolderResId()) {
                builder.placeholder(config.getPlaceHolderResId());
            }
            if (null != config.getSize()) {
                builder.override(config.getSize().getWidth(), config.getSize().getHeight());
            }
            if (null != listener) {
                setListener(builder, listener);
            }
            if (null != config.getThumbnailUrl()) {
                BitmapRequestBuilder thumbnailRequest = Glide.with(context).load(config.getThumbnailUrl()).asBitmap();
                builder.thumbnail(thumbnailRequest).into(view);
            } else {
                setTargetView(builder, config, view);
            }
        } catch (Exception e) {
            view.setImageResource(config.getErrorResId());
        }
    }

    private static void setListener(GenericRequestBuilder request, final ImgLoadListener listener) {
        request.listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                if (!e.getMessage().equals("divide by zero")) {
                    listener.onError();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                listener.onSuccess();
                return false;
            }
        });
    }

    private static void setTargetView(GenericRequestBuilder request, GlideLoadConfig config, ImageView view) {
        //set targetView
        if (null != config.getSimpleTarget()) {
            request.into(config.getSimpleTarget());
        } else if (null != config.getViewTarget()) {
            request.into(config.getViewTarget());
        } else if (null != config.getNotificationTarget()) {
            request.into(config.getNotificationTarget());
        } else if (null != config.getAppWidgetTarget()) {
            request.into(config.getAppWidgetTarget());
        } else {
            request.into(view);
        }
    }

    /**
     * 加载bitmap
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void loadBitmap(Context context, Object url, final BitmapLoadingListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            Glide.with(context).
                    load(url).
                    asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    dontAnimate().
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (listener != null) {
                                listener.onSuccess(resource);
                            }
                        }
                    });
        }
    }

    /**
     * 高优先级加载
     *
     * @param url
     * @param imageView
     * @param listener
     */
    public static void loadImageWithHighPriority(Object url, ImageView imageView, final ImgLoadListener listener) {
        if (url == null) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            Glide.with(imageView.getContext()).
                    load(url).
                    asBitmap().
                    priority(Priority.HIGH).
                    dontAnimate().
                    listener(new RequestListener<Object, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            if (null != listener) {
                                listener.onError();
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (null != listener) {
                                listener.onSuccess();
                            }
                            return false;
                        }
                    }).into(imageView);
        }
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除所有缓存
     *
     * @param context
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    public static synchronized long getDiskCacheSize(Context context) {
        long size = 0L;
        File cacheDir = PathUtils.getImgDiskCacheDir(context);

        if (cacheDir != null && cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                File[] arr$ = files;
                int len$ = files.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    File imageCache = arr$[i$];
                    if (imageCache.isFile()) {
                        size += imageCache.length();
                    }
                }
            }
        }

        return size;
    }

    public static void clearTarget(Context context, String uri) {
        if (UbabyGlideModule.cache != null && uri != null) {
            UbabyGlideModule.cache.delete(new StringSignature(uri));
            Glide.get(context).clearMemory();
        }
    }

    public static void clearTarget(View view) {
        Glide.clear(view);
    }

    public static File getTarget(Context context, String uri) {
        return UbabyGlideModule.cache != null && uri != null ? UbabyGlideModule.cache.get(new StringSignature(uri)) : null;
    }

    //默认配置
    public static GlideLoadConfig defConfig = new GlideLoadConfig.Builder().
            setCropType(GlideLoadConfig.CENTER_CROP).
            setAsBitmap(true).
//            setPlaceHolderResId(R.drawable.bg_loading).
//            setErrorResId(R.drawable.bg_error).
        setDiskCacheStrategy(GlideLoadConfig.DiskCache.SOURCE).
                    setPrioriy(GlideLoadConfig.LoadPriority.HIGH).build();

}
