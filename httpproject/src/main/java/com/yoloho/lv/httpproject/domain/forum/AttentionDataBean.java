package com.yoloho.lv.httpproject.domain.forum;

import com.yoloho.lv.httpproject.domain.BaseDoMain;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注人实体类
 * Created by mylinux on 16/05/19.
 */
public class AttentionDataBean extends BaseDoMain {
    public List<AttentionInfoBean> dataList = new ArrayList<>();
    public List<AttentionInfoBean> recommendList = new ArrayList<>();
    public String refreshtime;
}
