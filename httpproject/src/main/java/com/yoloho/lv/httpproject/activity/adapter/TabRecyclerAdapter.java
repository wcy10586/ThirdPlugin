package com.yoloho.lv.httpproject.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoloho.lv.httpproject.R;
import com.yoloho.lv.httpproject.domain.forum.TopicInfo;

import java.util.List;

/**
 * Created by mylinux on 16/09/14.
 */
public class TabRecyclerAdapter  extends RecyclerView.Adapter<TabRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<TopicInfo> list;
    private OnItemClickListener listener;

    public TabRecyclerAdapter(Context context, List<TopicInfo> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.superme_recycleview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.contentTxt);
        }
    }

    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}