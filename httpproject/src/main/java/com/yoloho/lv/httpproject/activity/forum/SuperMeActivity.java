package com.yoloho.lv.httpproject.activity.forum;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.BaseActivity;
import com.yoloho.lv.httpproject.activity.adapter.TabPagerAdapter;
import com.yoloho.lv.httpproject.activity.adapter.TabRecyclerAdapter;
import com.yoloho.lv.httpproject.domain.forum.TopicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylinux on 16/09/14.
 */
public class SuperMeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superme);
        initViews();
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initTabLayout();
    }

    private void initTabLayout() {

        List<String> tabList = new ArrayList<>();
        tabList.add("Tab1");
        tabList.add("Tab2");
        tabList.add("Tab3");
        tabList.add("Tab4");
        tabList.add("Tab5");
        tabList.add("Tab6");
        tabList.add("Tab7");
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        //此处代码设置无效，不知道为啥？？？xml属性是可以的
//        mTabLayout.setTabTextColors(android.R.color.white, android.R.color.holo_red_dark);//设置TabLayout两种状态
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab4"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab5"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab6"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab7"));

        List<View> views = new ArrayList<>();
        List<TopicInfo> list = new ArrayList<>();
        TopicInfo info = new TopicInfo();
        list.add(info);
        for (int i = 0; i < 7; i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.superme_tabs_layout, null, false);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            TabRecyclerAdapter adapter = new TabRecyclerAdapter(this, list);
            recyclerView.setAdapter(adapter);
            views.add(v);
        }
        PagerAdapter mPagerAdapter = new TabPagerAdapter(views, tabList);
        mViewPager.setAdapter(mPagerAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);//给Tabs设置适配器

    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
}
