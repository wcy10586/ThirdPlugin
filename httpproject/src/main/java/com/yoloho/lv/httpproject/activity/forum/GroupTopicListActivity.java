package com.yoloho.lv.httpproject.activity.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.utils.api.apimanager.ForumAPIManager;
import com.yoloho.lv.httpproject.utils.api.callback.HttpResultCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 小组话题列表接口的使用
 * Created by mylinux on 16/06/15.
 */
public class GroupTopicListActivity extends BaseAppCompatActivity implements View.OnClickListener {
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

    private HttpResultCallBack mHttpResultCallBack;

    private void getData() {
        if (null == mHttpResultCallBack) {
            mHttpResultCallBack = new HttpResultCallBack() {
                @Override
                public void onSuccess(JSONObject json) throws JSONException, SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
                    resultTxt.setText(json.toString());
                }

                @Override
                public void onError(JSONObject json) {
                    resultTxt.setText("数据请求错误");
                }
            };
        }
        Map<String, String> params = new HashMap<>();
        params.put("topic_group_id", "160");
//        params.put("topic_type_id", "0");
        ForumAPIManager.getInstance().getGroupListData(mHttpResultCallBack, params);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.query_data_btn) {
            getData();
        }
    }

    Button queryBtn;
    TextView resultTxt;
}
