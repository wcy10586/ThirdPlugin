package com.yoloho.lv.httpproject.activity.forum.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.activity.forum.AddTopicActivity;

import java.util.ArrayList;

/**
 * 发表新话题页面的图片展示的适配器
 * Created by mylinux on 16/06/14.
 */
public class AddTopicImgGridAdapter extends BaseAdapter {
    private ArrayList<String> listUrls;
    private LayoutInflater inflater;
    private Context mContext;
    private final String addpic = AddTopicActivity.ADD_NEW_PIC_FLAG;

    public AddTopicImgGridAdapter(ArrayList<String> listUrls, Context mcontext) {
        this.listUrls = listUrls;
        if (listUrls.size() == 7) {
            listUrls.remove(listUrls.size() - 1);
        }
        mContext = mcontext;
        inflater = LayoutInflater.from(mcontext);
    }

    public int getCount() {
        return listUrls.size();
    }

    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.forum_addtopic_gv_list_item, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String path = listUrls.get(position);
        if (addpic.equals(path)) {
            holder.image.setImageResource(R.drawable.trade_btn_add_picture_normal);
        } else {
            Glide.with(mContext)
                    .load(path)
                    .placeholder(R.drawable.default_error)
                    .error(R.drawable.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(holder.image);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }
}