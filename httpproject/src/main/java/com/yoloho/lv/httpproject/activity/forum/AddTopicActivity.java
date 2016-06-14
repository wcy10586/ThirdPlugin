package com.yoloho.lv.httpproject.activity.forum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.yoloho.hym.photopicker.ImageCaptureManager;
import com.yoloho.hym.photopicker.PhotoPickerActivity;
import com.yoloho.hym.photopicker.PhotoPreviewActivity;
import com.yoloho.hym.photopicker.SelectModel;
import com.yoloho.hym.photopicker.intent.PhotoPickerIntent;
import com.yoloho.hym.photopicker.intent.PhotoPreviewIntent;
import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.activity.forum.topic.AddTopicImgGridAdapter;

import org.json.JSONArray;

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
    public static final int REQUEST_CAMERA_CODE = 0x010;
    public static final int REQUEST_PREVIEW_CODE = 0x020;
    public static final String ADD_NEW_PIC_FLAG="newpicflag";
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类

    private String depp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_act_addtopic);
        initViews();
        initPage();
    }

    private void initPage() {
//        单文件上传@Multipart,每个Part对应一个RequestBody。
        File file = new File(Environment.getExternalStorageDirectory(), "icon.png");
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png", photoRequestBody);
        setGridView();
    }

    private void setGridView() {
        // preview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if (ADD_NEW_PIC_FLAG.equals(imgs)) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(AddTopicActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(6); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(AddTopicActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        imagePaths.add(ADD_NEW_PIC_FLAG);
        gridAdapter = new AddTopicImgGridAdapter(imagePaths, this);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d("tag_selected", "list: " + "list = [" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d("tag_selected", "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains(ADD_NEW_PIC_FLAG)) {
            paths.remove(ADD_NEW_PIC_FLAG);
        }
        paths.add(ADD_NEW_PIC_FLAG);
        imagePaths.addAll(paths);
        gridAdapter = new AddTopicImgGridAdapter(imagePaths, this);
        gridView.setAdapter(gridAdapter);
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFile() {
        String fileUrl = "http://down.360safe.com/360/inst.exe";

    }

    private void initViews() {
        gridView = (GridView) findViewById(R.id.gridView);
        mButton = (Button) findViewById(R.id.sendTopicBtn);
        textView = (EditText) findViewById(R.id.et_context);
        backBtn = findViewById(R.id.left_btn);
        backBtn.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.left_btn) {
            //TODO 退出前判断是否有未完成,正在编辑的话题,询问用户是否保存
            finish();
        } else if (id == R.id.sendTopicBtn) {
            depp =textView.getText().toString().trim()!=null?textView.getText().toString().trim():"好孕妈--生的漂亮";
        }
    }

    private GridView gridView;
    private AddTopicImgGridAdapter gridAdapter;
    private Button mButton;
    private EditText textView;
    private View backBtn;
}
