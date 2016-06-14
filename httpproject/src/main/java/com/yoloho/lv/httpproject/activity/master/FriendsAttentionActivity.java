package com.yoloho.lv.httpproject.activity.master;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.domain.forum.AttentionDataBean;
import com.yoloho.lv.httpproject.domain.forum.AttentionInfoBean;
import com.yoloho.lv.httpproject.utils.api.apimanager.ForumAPIManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 关注人列表
 * 1.更换网络访问方式,解析数据方式,对关注人页面的接口测试
 * 2.访问速度监听,数据解析的处理
 * Created by mylinux on 16/06/13.
 */
public class FriendsAttentionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_act_topicdetail);
        initPage();
    }

    private void initPage() {
        queryBtn = (Button) findViewById(R.id.query_data_btn);
        resultTxt = (TextView) findViewById(R.id.display_data_txt);
        queryBtn.setOnClickListener(this);
    }


    private void parseJson() {
        Map<String, String> params = new HashMap<>();
        params.put("module", "1");
        params.put("nowPage", "0");
        params.put("refreshtime", "0");
        Call<AttentionDataBean> call = ForumAPIManager.getInstance().getAttentionData(params);
        call.enqueue(new Callback<AttentionDataBean>() {
            @Override
            public void onResponse(Call<AttentionDataBean> call, Response<AttentionDataBean> response) {
                AttentionDataBean bean = response.body();
                if (null != bean) {
                    if (bean.recommendList != null) {
                        final int size = bean.recommendList.size();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < size; i++) {
                            final AttentionInfoBean info =bean.recommendList.get(i);
                            sb.append(info.title);
                            sb.append("\n");
                            sb.append(info.content);
                            sb.append("\n");
                            sb.append(info.contents);
                        }
                        resultTxt.setText(sb.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<AttentionDataBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        final int id = v.getId();
        if (id == R.id.query_data_btn) {
            parseJson();
        }
    }

    Button queryBtn;
    TextView resultTxt;
}
