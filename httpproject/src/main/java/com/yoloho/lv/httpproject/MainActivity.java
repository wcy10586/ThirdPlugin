package com.yoloho.lv.httpproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yoloho.actrouters.ActRouters;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.activity.forum.AddTopicActivity;
import com.yoloho.lv.httpproject.activity.forum.CircleTopicListAct;
import com.yoloho.lv.httpproject.activity.forum.EditorTopicActivity;
import com.yoloho.lv.httpproject.activity.forum.GroupTopicListActivity;
import com.yoloho.lv.httpproject.activity.forum.TopicDetailActivity;
import com.yoloho.lv.httpproject.activity.master.FriendsAttentionActivity;
import com.yoloho.lv.httpproject.domain.baby.BabyInfoModel;
import com.yoloho.lv.httpproject.domain.forum.AttentionInfoBean;
import com.yoloho.lv.httpproject.domain.forum.Piclist;
import com.yoloho.lv.httpproject.domain.user.UserInfoModel;
import com.yoloho.lv.httpproject.utils.api.apimanager.BabyAPIManager;
import com.yoloho.lv.httpproject.utils.api.apimanager.UserAPIManager;
import com.yoloho.lv.httpproject.utils.imgs.GlideUtils;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <ul>
 * <li>T-MVP：泛型深度解耦下的MVP大瘦身
 * http://www.jianshu.com/p/b49958e1889d
 * </li>
 * </ul>
 *
 * @author lv
 */
public class MainActivity extends BaseAppCompatActivity {

    private TextView contentTxt, rxjavaTxtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initViews();
        initData();
    }

    private void initViews() {
        contentTxt = (TextView) findViewById(R.id.contentTxt);
        rxjavaTxtBtn = (TextView) findViewById(R.id.rxjavaTxtBtn);
        Glide.with(MainActivity.this).load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png").skipMemoryCache(true).into(330, 600);
    }

    private void initData() {
//        getBabyInfoData();
        Piclist[] piclist = new Piclist[10];
        for (int i = 0; i < 9; i++) {
            Piclist item = new Piclist();
            item.linkUrl = "fragments.pn" + i;
            piclist[i] = item;
        }

        //遍历persionList集合下每一组的图片集合
        ArrayList<AttentionInfoBean> persionList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            AttentionInfoBean bean = new AttentionInfoBean();
            Piclist pic = new Piclist();
            pic.oriPic = "http://www.apkbus.com/blog-705730-60454.html?_dsign=6c19f5d8" + "--------" + i;
            ArrayList<Piclist> imglist = new ArrayList<>();
            imglist.add(pic);
            bean.piclist = imglist;
            persionList.add(bean);
        }


        Observable.from(persionList).flatMap(new Func1<AttentionInfoBean, Observable<Piclist>>() {
            @Override
            public Observable<Piclist> call(AttentionInfoBean attentionInfoBean) {
                return Observable.from(attentionInfoBean.piclist);
            }
        }).subscribe(new Subscriber<Piclist>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Piclist piclist) {
                //处理
                String str = contentTxt.getText() + "/n" + piclist.oriPic;
                contentTxt.setText(str);
            }
        });
        Observable.just("getDiskCacheSize")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        long size = GlideUtils.getDiskCacheSize(MainActivity.this);
                        File file= Glide.getPhotoCacheDir(MainActivity.this);
                        if(null!=file){
                            return file.getPath()+" size = "+(size/(1024*1024f))+"M";
                        }
                        return size+"";
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        String str= contentTxt.getText().toString();
                        contentTxt.setText(str+"/n 缓存目录的大小:"+s);
                    }
                });
    }

    private Call babyCall = null;

    private void getBabyInfoData() {
        babyCall = BabyAPIManager.getInstance().getBabyInfoRequestCall();
        babyCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                BabyInfoModel result = (BabyInfoModel) response.body();
                if (null != result) {
//                    contentTxt.setText(result.list.toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void getUserInfoData() {
        Call<UserInfoModel> call = UserAPIManager.getInstance().getUserInfoRequestCall();
        call.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {

            }

            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(MainActivity.this, TopicDetailActivity.class);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.action_act1) {
            Intent mIntent = new Intent(MainActivity.this, CircleTopicListAct.class);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.action_act2) {
           /* Intent mIntent = new Intent(MainActivity.this, PublicWebActivity.class);
            startActivity(mIntent);*/
            ActRouters.open(MainActivity.this, "haoyunma://publicwebact");
            return true;
        } else if (id == R.id.action_act3) {
//            Intent mIntent = new Intent(MainActivity.this, ShowBigImgActivity.class);
//            startActivity(mIntent);
            ActRouters.open(MainActivity.this, "haoyunma://showbigimgact");
            return true;
        } else if (id == R.id.action_act4) {
            Intent mIntent = new Intent(MainActivity.this, FriendsAttentionActivity.class);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.action_act5) {
            Intent mIntent = new Intent(MainActivity.this, AddTopicActivity.class);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.action_act6) {
            Intent mIntent = new Intent(MainActivity.this, GroupTopicListActivity.class);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.action_act7) {
            Intent mIntent = new Intent(MainActivity.this, EditorTopicActivity.class);
            startActivity(mIntent);
            return true;
        } else if (id == R.id.action_act8) {
//            Intent mIntent = new Intent(MainActivity.this, SuperMeActivity.class);
//            startActivity(mIntent);
            Uri uri = Uri.parse("haoyunma://homepage");
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
