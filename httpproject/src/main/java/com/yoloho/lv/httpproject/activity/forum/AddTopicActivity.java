package com.yoloho.lv.httpproject.activity.forum;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoloho.hym.photopicker.SelectModel;
import com.yoloho.hym.photopicker.intent.PhotoPickerIntent;
import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件上传
 * 1.单文件上传@Multipart
 * Created by mylinux on 16/06/13.
 */
public class AddTopicActivity extends BaseAppCompatActivity implements View.OnClickListener {
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

//        单文件上传@Multipart,每个Part对应一个RequestBody。
        File file = new File(Environment.getExternalStorageDirectory(), "icon.png");
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png", photoRequestBody);

//        RequestBody.create(MediaType.parse("image/*"), file);

    }

    private void downloadFile() {
        String fileUrl = "http://down.360safe.com/360/inst.exe";

    }

    final int REQUEST_CAMERA_CODE = 0x035;
    ArrayList<String> imagePaths = new ArrayList<>();
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.query_data_btn) {
            PhotoPickerIntent intent = new PhotoPickerIntent(AddTopicActivity.this);
            intent.setSelectModel(SelectModel.MULTI); //
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(6); // 最多选择照片数量，默认为6
            intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }
    }
    Button queryBtn;
    TextView resultTxt;
}
