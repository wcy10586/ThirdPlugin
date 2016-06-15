package com.yoloho.lv.httpproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.activity.forum.AddTopicActivity;
import com.yoloho.lv.httpproject.activity.forum.CircleTopicListAct;
import com.yoloho.lv.httpproject.activity.forum.GroupTopicListActivity;
import com.yoloho.lv.httpproject.activity.forum.TopicDetailActivity;
import com.yoloho.lv.httpproject.activity.master.FriendsAttentionActivity;
import com.yoloho.lv.httpproject.activity.master.ShowBigImgActivity;
import com.yoloho.lv.httpproject.activity.web.PublicWebActivity;
import com.yoloho.lv.httpproject.domain.baby.BabyInfoModel;
import com.yoloho.lv.httpproject.domain.forum.Piclist;
import com.yoloho.lv.httpproject.domain.forum.TopicInfo;
import com.yoloho.lv.httpproject.domain.user.UserInfoModel;
import com.yoloho.lv.httpproject.utils.api.apimanager.BabyAPIManager;
import com.yoloho.lv.httpproject.utils.api.apimanager.UserAPIManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

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
        getBabyInfoData();

        //
        Piclist[] piclist = new Piclist[10];
        for (int i = 0; i < 9; i++) {
            Piclist item = new Piclist();
            item.linkUrl = "fragments.pn" + i;
            piclist[i] = item;
        }

        final Observable<String> ob = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("bbbbbbbbbbbbbbbbbbb");
            }
        });
        final Observable ob2 = Observable.from(piclist).map(new Func1<Piclist, String>() {
            @Override
            public String call(Piclist o) {
                return o.linkUrl;
            }
        });
        final Observable ob4 = Observable.from(piclist).flatMap(new Func1<Piclist, Observable<TopicInfo>>() {
            @Override
            public Observable<TopicInfo> call(Piclist piclist) {
                return null;
            }
        });
        String[] words = {"aaa", "sss", "ddd"};
        Observable ob3 = Observable.from(words);

        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                contentTxt.setText(s);
                ob2.subscribe();
            }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                contentTxt.setText(s);
            }
        };
        rxjavaTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob.subscribe(observer);
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
        }else if(id==R.id.action_act1){
            Intent mIntent = new Intent(MainActivity.this, CircleTopicListAct.class);
            startActivity(mIntent);
            return true;
        }else if(id==R.id.action_act2){
            Intent mIntent = new Intent(MainActivity.this, PublicWebActivity.class);
            startActivity(mIntent);
            return true;
        }else if(id==R.id.action_act3){
            Intent mIntent = new Intent(MainActivity.this, ShowBigImgActivity.class);
            startActivity(mIntent);
            return true;
        }else if(id==R.id.action_act4){
            Intent mIntent = new Intent(MainActivity.this, FriendsAttentionActivity.class);
            startActivity(mIntent);
            return true;
        }else if(id==R.id.action_act5){
            Intent mIntent = new Intent(MainActivity.this, AddTopicActivity.class);
            startActivity(mIntent);
            return true;
        }else if(id==R.id.action_act6){
            Intent mIntent = new Intent(MainActivity.this, GroupTopicListActivity.class);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
