package com.yoloho.lv.httpproject.activity.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseAppCompatActivity;
import com.yoloho.lv.httpproject.actmenus.dropdownmenu.DropdownButton;
import com.yoloho.lv.httpproject.actmenus.dropdownmenu.DropdownListView;
import com.yoloho.lv.httpproject.actmenus.dropdownmenu.bean.DropdownItemObject;
import com.yoloho.lv.httpproject.actmenus.dropdownmenu.bean.TopicLabelObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylinux on 16/05/25.
 */
public class CircleTopicListAct extends BaseAppCompatActivity implements View.OnClickListener {
    //第一个tab--我的讨论{全部讨论,我的讨论}
    private static final int ID_TYPE_ALL = 0;
    private static final int ID_TYPE_MY = 1;
    private static final String TYPE_ALL = "全部讨论";
    private static final String TYPE_MY = "我的讨论";
    //第二个tab--全部标签{全部标签,自定义的....}
    private static final int ID_LABEL_ALL = -1;
    private static final String LABEL_ALL = "全部标签";
    //第三个tab--最后评论排序{最后评论排序,发布时间排序,热门排序}
    private static final String ORDER_REPLY_TIME = "最后评论排序";
    private static final String ORDER_PUBLISH_TIME = "发布时间排序";
    private static final String ORDER_HOT = "热门排序";
    private static final int ID_ORDER_REPLY_TIME = 51;
    private static final int ID_ORDER_PUBLISH_TIME = 49;
    private static final int ID_ORDER_HOT = 53;

    ListView listView;
    TextView selectedResultTxt;
    View mask;
    DropdownButton chooseType, chooseLabel, chooseOrder;
    //第一个tab ;第二个tab;第三个tab
    DropdownListView dropdownType, dropdownLabel, dropdownOrder;

    /*进入,退出,消失动画*/
    Animation dropdown_in, dropdown_out, dropdown_mask_out;

    private List<TopicLabelObject> labels = new ArrayList<>();

    private DropdownButtonsController dropdownButtonsController = new DropdownButtonsController();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_act_circletopiclist);
        initPage();
    }

    private void initPage() {
        listView = (ListView) findViewById(R.id.listView);
        selectedResultTxt = (TextView) findViewById(R.id.selectedResultTxt);
        //遮罩
        mask = findViewById(R.id.mask);
        //三个按钮
        chooseType = (DropdownButton) findViewById(R.id.chooseType);
        chooseLabel = (DropdownButton) findViewById(R.id.chooseLabel);
        chooseOrder = (DropdownButton) findViewById(R.id.chooseOrder);
        //每一个tab按钮的下拉列表
        dropdownType = (DropdownListView) findViewById(R.id.dropdownType);
        dropdownLabel = (DropdownListView) findViewById(R.id.dropdownLabel);
        dropdownOrder = (DropdownListView) findViewById(R.id.dropdownOrder);

        //进入,退出,消失动画
        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_out);
        //这个是控制遮罩显示的动画的
        dropdown_mask_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_mask_out);

        //控制器,初始化
        dropdownButtonsController.init();

        //id count name
        TopicLabelObject topicLabelObject1 = new TopicLabelObject(1, 1, "Fragment");
        labels.add(topicLabelObject1);
        TopicLabelObject topicLabelObject2 = new TopicLabelObject(2, 1, "CustomView");
        labels.add(topicLabelObject2);
        TopicLabelObject topicLabelObject3 = new TopicLabelObject(3, 1, "Service");
        labels.add(topicLabelObject3);
        TopicLabelObject topicLabelObject4 = new TopicLabelObject(4, 1, "BroadcastReceiver");
        labels.add(topicLabelObject4);
        TopicLabelObject topicLabelObject5 = new TopicLabelObject(5, 1, "Activity");
        labels.add(topicLabelObject5);


        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownButtonsController.hide();
            }
        });


//        dropdownButtonsController.flushCounts();
        dropdownButtonsController.flushAllLabels();
        dropdownButtonsController.flushMyLabels();

    }

    @Override
    public void onClick(View v) {

    }

    private class DropdownButtonsController implements DropdownListView.Container {
        //当前正在展开的下拉列表
        private DropdownListView currentDropdownList;

        private List<DropdownItemObject> datasetType = new ArrayList<>(2);//全部讨论
        private List<DropdownItemObject> datasetOrder = new ArrayList<>(3);//评论排序

        //用来存储的是当前展示的标签
        private List<DropdownItemObject> datasetAllLabel = new ArrayList<>();//全部标签
        /*临时存储器,存储的是标签中的一种*/
        private List<DropdownItemObject> datasetMyLabel = new ArrayList<>();//我的标签

        //代表真正的第二个标签<全部标签>的数据集合存储容器
        private List<DropdownItemObject> datasetLabel = datasetAllLabel;//标签集合   默认是全部标签


        @Override
        public void show(DropdownListView view) {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.setVisibility(View.GONE);
                currentDropdownList.button.setChecked(false);
            }
            currentDropdownList = view;
            mask.clearAnimation();
            mask.setVisibility(View.VISIBLE);
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_in);
            currentDropdownList.setVisibility(View.VISIBLE);
            currentDropdownList.button.setChecked(true);
        }

        @Override
        public void hide() {
            if (currentDropdownList != null) {
                currentDropdownList.clearAnimation();
                currentDropdownList.startAnimation(dropdown_out);
                currentDropdownList.button.setChecked(false);
                mask.clearAnimation();
                mask.startAnimation(dropdown_mask_out);
            }
            currentDropdownList = null;
        }

        @Override
        public void onSelectionChanged(DropdownListView view) {
            Log.e("tag_gg", "onSelectionChanged = " + view);
            if (view == dropdownType) {
//                updateLabels(getCurrentLabels());
            }
            if (null != view)
                selectedResultTxt.setText(view.current.text);

        }

        void reset() {
            chooseType.setChecked(false);
            chooseLabel.setChecked(false);
            chooseOrder.setChecked(false);

            dropdownType.setVisibility(View.GONE);
            dropdownLabel.setVisibility(View.GONE);
            dropdownOrder.setVisibility(View.GONE);
            mask.setVisibility(View.GONE);

            dropdownType.clearAnimation();
            dropdownLabel.clearAnimation();
            dropdownOrder.clearAnimation();
            mask.clearAnimation();
        }

        void init() {
            reset();
            //我的讨论
            datasetType.add(new DropdownItemObject(TYPE_ALL, ID_TYPE_ALL, "all"));//全部讨论
            datasetType.add(new DropdownItemObject(TYPE_MY, ID_TYPE_MY, "my"));//我的讨论
            dropdownType.bind(datasetType, chooseType, this, ID_TYPE_ALL);//初始化,进行绑定


            //全部标签
            final DropdownItemObject dropdownItem = new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null);
            //后缀参数的设置
            dropdownItem.suffix = (dropdownType.current == null ? "" : dropdownType.current.suffix);
            datasetAllLabel.add(dropdownItem);
            //此集合初始化只存储全部标签,不带后缀修饰
            datasetMyLabel.add(new DropdownItemObject(LABEL_ALL, ID_LABEL_ALL, null));

            //初始化,简单的
            datasetLabel = datasetAllLabel;
            //全部标签绑定,数据处理
            dropdownLabel.bind(datasetLabel, chooseLabel, this, ID_LABEL_ALL);

            //排序
            datasetOrder.add(new DropdownItemObject(ORDER_REPLY_TIME, ID_ORDER_REPLY_TIME, "51"));
            datasetOrder.add(new DropdownItemObject(ORDER_PUBLISH_TIME, ID_ORDER_PUBLISH_TIME, "49"));
            datasetOrder.add(new DropdownItemObject(ORDER_HOT, ID_ORDER_HOT, "53"));
            dropdownOrder.bind(datasetOrder, chooseOrder, this, ID_ORDER_REPLY_TIME);

            dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentDropdownList == null) {
                        reset();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


        ////////以下纯属业务逻辑,不需要理解太深,只要知道控件图和使用即可

        //刷新第一个标签的数量
        public void flushCounts() {

            datasetType.get(ID_TYPE_ALL).suffix = " (" + "5" + ")";
            datasetType.get(ID_TYPE_MY).suffix = " (" + "3" + ")";
            dropdownType.flush();
            dropdownLabel.flush();
        }

        //获取当前标签集合<全部标签>,主要是判断到底应该是使用datasetMyLabel集合,还是使用datasetAllLabel
        //两个集合的区别在于是否有后缀
        private List<DropdownItemObject> getCurrentLabels() {
            return dropdownType.current != null && dropdownType.current.id == ID_TYPE_MY ? datasetMyLabel : datasetAllLabel;
        }

        //<全部标签>,也就是第二个tab
        void updateLabels(List<DropdownItemObject> targetList) {
            if (targetList == getCurrentLabels()) {
                datasetLabel = targetList;
                dropdownLabel.bind(datasetLabel, chooseLabel, this, dropdownLabel.current.id);
            }
        }


        void flushAllLabels() {
            flushLabels(datasetAllLabel);
        }

        //
        void flushMyLabels() {
            flushLabels(datasetMyLabel);
        }

        //我的标签的数据,动态初始化
        private void flushLabels(List<DropdownItemObject> targetList) {

            while (targetList.size() > 1) targetList.remove(targetList.size() - 1);
            for (int i = 0, n = 5; i < n; i++) {

                int id = labels.get(i).id;
                String name = labels.get(i).name;
                if (TextUtils.isEmpty(name)) continue;
                int topicsCount = labels.get(i).count;
                // 只有all才做0数量过滤，因为my的返回数据总是0
                if (topicsCount == 0 && targetList == datasetAllLabel) continue;
                DropdownItemObject item = new DropdownItemObject(name, id, String.valueOf(id));
                if (targetList == datasetAllLabel)
                    item.suffix = "(5)";
                targetList.add(item);
            }
            updateLabels(targetList);
        }
    }
}
