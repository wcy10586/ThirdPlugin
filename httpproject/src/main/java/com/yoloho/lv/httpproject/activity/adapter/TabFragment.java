package com.yoloho.lv.httpproject.activity.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoloho.lv.httpproject.R;

/**
 * Created by mylinux on 16/09/14.
 */
public class TabFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;

    private String des[] = {"云层里的阳光", "好美的海滩", "好美的海滩", "夕阳西下的美景", "夕阳西下的美景"
            , "夕阳西下的美景", "夕阳西下的美景", "夕阳西下的美景", "好美的海滩"};



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.superme_tabs_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();
    }


    private void initData() {
    }
}
