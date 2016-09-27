package com.yoloho.lv.httpproject.activity.master;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoloho.annotation.Router;
import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.utils.imgs.GlideLoadConfig;
import com.yoloho.lv.httpproject.utils.imgs.GlideUtils;

import java.lang.ref.WeakReference;

/**
 * 利用glide下载图片并显示
 * 1.glide图库的联系使用
 * Created by mylinux on 16/06/13.
 */
@Router("showbigimgact")
public class ShowBigImgActivity extends BaseAppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubaby_act_showbigimg);
        initPage();
    }

    private void initPage() {
        queryBtn = (Button) findViewById(R.id.query_data_btn);
        resultTxt = (TextView) findViewById(R.id.display_data_txt);
        queryBtn.setOnClickListener(this);
        findViewById(R.id.query_data_btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.query_data_btn) {
            ImageView view = (ImageView) findViewById(R.id.contentBg);
//            Log.e("glide", "view=" + view.getDrawable());
//            Glide.with(TopicDetailActivity.this).load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png").into(view);
//            for (int i = 0; i < 10; i++) {
//                Glide.with(TopicDetailActivity.this).using(new ProgressModelLoader(mainHandler)).load(pics[6]).into(view);
//                Log.e("glide-load", "bbbbbbbbbb");
//            }
//            Glide.with(ShowBigImgActivity.this).using(new ProgressModelLoader(mainActivityHandler)).load(pics[6]).into(view);
//            SaveImageTask saveImgTask = new SaveImageTask(TopicDetailActivity.this);
//            saveImgTask.execute(pics[5]);
//            Log.e("glide", "view22222=" + view.getDrawable());
            String gif = "http://image24.360doc.com/DownloadImg/2011/03/0513/9707070_3.gif";
            GlideLoadConfig config2 = GlideLoadConfig.parseBuilder(GlideUtils.defConfig).setAsGif(true).
                    setSkipMemoryCache(true).
                    setDiskCacheStrategy(GlideLoadConfig.DiskCache.NONE).
                    build();
            GlideUtils.loadGif(view, gif, config2, null);
        } else if (id == R.id.query_data_btn2) {
            ImageView view = (ImageView) findViewById(R.id.contentBg);
            int index = (int) (Math.random() * 6);
            GlideUtils.loadStringRes(view, pics[index], GlideUtils.defConfig, null);
        }
    }

    String[] pics = {"http://image96.360doc.com/DownloadImg/2016/04/2201/70258332_1.jpg",
            "http://image96.360doc.com/DownloadImg/2016/04/2201/70258332_2.jpg",
            "http://image96.360doc.com/DownloadImg/2016/04/2201/70258332_3.jpg",
            "http://image96.360doc.com/DownloadImg/2016/04/2201/70258332_4.jpg",
            "http://image96.360doc.com/DownloadImg/2016/04/2201/70258332_5.jpg",
            "http://image96.360doc.com/DownloadImg/2016/04/2201/70258332_6.jpg",
            "http://img.haoyunma.com/offline/topic/TOPIC_IMAGE_1462933697321.jpeg@1080w_1590h"};
    private final MainActivityHandler mainActivityHandler = new MainActivityHandler(this);

    private static class MainActivityHandler extends Handler {
        private final WeakReference<ShowBigImgActivity> mActivity;

        public MainActivityHandler(ShowBigImgActivity activity) {
            mActivity = new WeakReference<ShowBigImgActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ShowBigImgActivity activity = mActivity.get();
            if (activity != null) {
                Log.e("tag_pic", "mainHandler msg.what =" + msg.what);
                switch (msg.what) {
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    Button queryBtn;
    TextView resultTxt;
}
