package com.yoloho.lv.httpproject.activity.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.domain.forum.TopicDetailResult;
import com.yoloho.lv.httpproject.domain.forum.TopicInfo;
import com.yoloho.lv.httpproject.utils.api.apimanager.ForumAPIManager;

import java.io.StringReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mylinux on 16/05/11.
 */
public class TopicDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private Call<TopicDetailResult> topicCall = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_topicdetailact);
        initPage();
    }


    private void initPage() {
        queryBtn = (Button) findViewById(R.id.query_data_btn);
        resultTxt = (TextView) findViewById(R.id.display_data_txt);
        queryBtn.setOnClickListener(this);
    }

    private String topicInfo ="{\n" +
            "        \"id\": 20767132,\n" +
            "        \"title\": \"急急！！请问我这是蚕豆病吗？？？\",\n" +
            "        \"status\": \"1\",\n" +
            "        \"createUid\": 202984757,\n" +
            "        \"nickName\": \"laizhengxin\",\n" +
            "        \"step_info\": \"孕15周6天\",\n" +
            "        \"createDate\": 1462954027000,\n" +
            "        \"stsChangeDate\": \"\",\n" +
            "        \"viewnum\": 6,\n" +
            "        \"favnum\": 0,\n" +
            "        \"answernum\": 0,\n" +
            "        \"lastAnswerTime\": \"\",\n" +
            "        \"ip\": \"121.33.236.34\",\n" +
            "        \"isanonymous\": \"0\",\n" +
            "        \"topicgroupid\": 47,\n" +
            "        \"topicGroupName\": \"孕检交流\",\n" +
            "        \"user_icon\": \"默认图片\",\n" +
            "        \"topicTypeId\": 6,\n" +
            "        \"isgrouptop\": \"0\",\n" +
            "        \"isalltop\": \"0\",\n" +
            "        \"source\": \"0\",\n" +
            "        \"isessence\": \"0\",\n" +
            "        \"ishot\": \"0\",\n" +
            "        \"isevent\": \"0\",\n" +
            "        \"ismedical\": \"0\",\n" +
            "        \"islock\": \"0\",\n" +
            "        \"lockDate\": \"\",\n" +
            "        \"isDown\": \"0\",\n" +
            "        \"downdate\": \"\",\n" +
            "        \"isfav\": \"0\",\n" +
            "        \"dateline\": \"\",\n" +
            "        \"content\": \"这是13周的体检结果，特地百度了一下，说是蚕豆病。。但是，我婚前明明检查过啊，当时并没说是蚕豆病。。那是不是我的宝宝有蚕豆病呢？？有知道的姐妹吗\",\n" +
            "        \"user_group_id\": 0,\n" +
            "        \"is_ban\": \"0\",\n" +
            "        \"piclist\": [\n" +
            "            {\n" +
            "                \"path\": \"http: //img.haoyunma.com/online/topic/TOPIC_IMAGE_1462954027101.jpeg\",\n" +
            "                \"width\": 720,\n" +
            "                \"height\": 272,\n" +
            "                \"order_num\": 0,\n" +
            "                \"ori_pic\": \"http: //img.haoyunma.com/online/topic/TOPIC_IMAGE_1462954027101.jpeg@165w_165h_1e_1c_80Q\",\n" +
            "                \"link_url\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"is_mixed\": \"0\",\n" +
            "        \"current_user_identity\": 0,\n" +
            "        \"create_sort_date\": 1462954027000,\n" +
            "        \"default_sort_date\": 1462954027000,\n" +
            "        \"is_on_whitelist\": \"0\",\n" +
            "        \"mix_content\": [\n" +
            "        ],\n" +
            "        \"canshare\": \"1\",\n" +
            "        \"share_url\": \"https: //h5.haoyunma.com/topic/id/9aff8093b06636f6f26b2fb01ed11d50\",\n" +
            "        \"viewstr\": \"0\",\n" +
            "        \"likecount\": \"0\",\n" +
            "        \"islike\": \"0\",\n" +
            "        \"userHonor\": \"\",\n" +
            "        \"relationKnowledge\": \"\",\n" +
            "        \"recomList\": [\n" +
            "        ],\n" +
            "        \"tagList\": [\n" +
            "        ]\n" +
            "    }";
    private void getTopicDetailData() {
        Call<TopicDetailResult> dataCall;
        if (null == topicCall) {
            topicCall = ForumAPIManager.getInstance().queryTopicDetailById();
        }
        dataCall = topicCall.clone();
        dataCall.enqueue(new Callback<TopicDetailResult>() {
            @Override
            public void onResponse(Call<TopicDetailResult> call, Response<TopicDetailResult> response) {
                TopicDetailResult result = (TopicDetailResult) response.body();
                if (null != result) {
                    try {
                        Gson g = new Gson();
                        JsonReader reader = new JsonReader(new StringReader(result.topic_info.toString().trim()));
                        reader.setLenient(true);
//                        TopicInfo mTopicInfo = g.fromJson(reader, TopicInfo.class);
                        TopicInfo mTopicInfo = g.fromJson(topicInfo, TopicInfo.class);
                        result.topicInfo=mTopicInfo;
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    resultTxt.setText("topic_info = " + result.topic_info);
                }
            }

            @Override
            public void onFailure(Call<TopicDetailResult> call, Throwable t) {

            }
        });
//        Call<TopicDetailResult> call = ForumAPIManager.getInstance().test();
//        call.enqueue(new Callback<TopicDetailResult>() {
//            @Override
//            public void onResponse(Call<TopicDetailResult> call, Response<TopicDetailResult> response) {
//                if (null != response.body()) {
//                    resultTxt.setText(response.body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TopicDetailResult> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.query_data_btn) {
            getTopicDetailData();
        }
    }

    Button queryBtn;
    TextView resultTxt;
}
