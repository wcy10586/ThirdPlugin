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

import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.activity.forum.TopicDetailActivity;
import com.yoloho.lv.httpproject.domain.baby.BabyInfoModel;
import com.yoloho.lv.httpproject.domain.user.UserInfoModel;
import com.yoloho.lv.httpproject.utils.api.apimanager.BabyAPIManager;
import com.yoloho.lv.httpproject.utils.api.apimanager.UserAPIManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseAppCompatActivity {

    private TextView contentTxt;

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
    }

    private void initData() {
        getBabyInfoData();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
