package com.yoloho.lv.httpproject.activity.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mylinux on 16/09/14.
 */
public class TabPagerAdapter extends PagerAdapter {
    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    private List<View> pageViews;
    List<String> tabList;
    public TabPagerAdapter(List<View> pageViews,List<String> mtabList) {
        super();
        this.tabList=mtabList;
        this.pageViews = pageViews;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null == pageViews)
            return super.getPageTitle(position);
        else
            return tabList.get(position);

    }

    @Override
    public int getCount() {
        if (null == pageViews)
            return 0;
        else
            return pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
    }

    /***
     * 获取每一个item
     */
    @Override
    public Object instantiateItem(View arg0, int position) {
        View view = null;
        if (viewMap.containsKey(position)) {
            view = viewMap.get(position);
        } else {
            view = pageViews.get(position);
            viewMap.put(position, view);
            ((ViewPager) arg0).addView(view);
        }
        return view;
    }
}
