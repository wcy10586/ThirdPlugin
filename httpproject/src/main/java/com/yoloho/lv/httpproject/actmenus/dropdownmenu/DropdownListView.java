package com.yoloho.lv.httpproject.actmenus.dropdownmenu;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.actmenus.dropdownmenu.bean.DropdownItemObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 通过ScrollView实现listview效果的列表选项
 * <ul><li>1.点击标签的下拉列表,单选</li></ul>
 * Created by lv on 2016/5/25.
 */
public class DropdownListView extends ScrollView {
    public LinearLayout linearLayout;

    public DropdownItemObject current;

    public List<? extends DropdownItemObject> list;

    public DropdownButton button;

    public DropdownListView(Context context) {
        this(context, null);
    }

    public DropdownListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*初始化,布局的处理*/
    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_list, this, true);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
    }

    /*处理业务数据,调用者需要刷新数据的时候,执行此方法,在没有必要的情况下,尽量最到少调用,最起码应该是精准调用*/
    public void flush() {
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                DropdownListItemView itemView = (DropdownListItemView) view;
                DropdownItemObject data = (DropdownItemObject) itemView.getTag();
                if (data == null) {
                    return;
                }
                boolean checked = data == current;
                String suffix = data.suffix;
                //处理每一个item的数据刷新
                itemView.bind(TextUtils.isEmpty(suffix) ? data.text : data.text + suffix, checked);
                //更新标签栏的文案为选中的选项
                if (checked) button.setText(data.text);
            }

        }
    }

    /**
     * 下拉列表,数据的绑定
     * 1.数据的集合
     * 2.点击的tab按钮的句柄
     * 3.回调,处理显示,收起,选中等操作
     * 4.初始化首次被标记选中的选项
     */
    public void bind(List<? extends DropdownItemObject> list, DropdownButton button, final Container container, int selectedId) {
        current = null;
        this.list = list;
        this.button = button;
        LinkedList<View> cachedDividers = new LinkedList<>();
        LinkedList<DropdownListItemView> cachedViews = new LinkedList<>();
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                cachedViews.add((DropdownListItemView) view);
            } else {
                cachedDividers.add(view);
            }
        }

        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        boolean isFirst = true;
        for (DropdownItemObject item : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                View divider = cachedDividers.poll();
                if (divider == null) {
                    divider = inflater.inflate(R.layout.dropdown_tab_list_divider, linearLayout, false);
                }
                linearLayout.addView(divider);
            }
            DropdownListItemView view = cachedViews.poll();
            if (view == null) {
                view = (DropdownListItemView) inflater.inflate(R.layout.dropdown_tab_list_item, linearLayout, false);
            }
            view.setTag(item);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DropdownItemObject data = (DropdownItemObject) v.getTag();
                    if (data == null) return;
                    DropdownItemObject oldOne = current;
                    current = data;
                    flush();
                    container.hide();
                    if (oldOne != current) {
                        container.onSelectionChanged(DropdownListView.this);
                    }
                }
            });
            linearLayout.addView(view);
            if (item.id == selectedId && current == null) {
                current = item;
            }
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisibility() == VISIBLE) {
                    container.hide();
                } else {
                    container.show(DropdownListView.this);
                }
            }
        });

        if (current == null && list.size() > 0) {
            current = list.get(0);
        }
        flush();
    }


    /**
     * 处理回调的接口
     */
    public static interface Container {
        void show(DropdownListView listView);

        void hide();

        void onSelectionChanged(DropdownListView view);
    }


}
